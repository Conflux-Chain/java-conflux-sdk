package org.cfx.crypto;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.cfx.rlp.RlpEncoder;
import org.cfx.rlp.RlpList;
import org.cfx.rlp.RlpString;
import org.cfx.rlp.RlpType;
import org.cfx.utils.Bytes;
import org.cfx.utils.Numeric;

/**
 * Create RLP encoded transaction, implementation as per p4 of the <a
 * href="http://gavwood.com/paper.pdf">yellow paper</a>.
 */
public class TransactionEncoder {

    private static final int CHAIN_ID_INC = 35;
    private static final int LOWER_REAL_V = 27;

    public static byte[] signMessage(RawTransaction rawTransaction, Credentials credentials) {
        byte[] encodedTransaction = encode(rawTransaction);
        Sign.SignatureData signatureData =
                Sign.signMessage(encodedTransaction, credentials.getEcKeyPair());

        return getEncodedData(rawTransaction, signatureData);
    }

    public static  byte[] getEncodedData(RawTransaction rawTransaction,Sign.SignatureData signatureData){

        //        // adjust V in signature
//        int headerByte = signatureData.getV()[0] - 27;
//        signatureData = new Sign.SignatureData((byte) headerByte, signatureData.getR(), signatureData.getS());
//        List<RlpType> fields = TransactionEncoder.asRlpValues(rawTransaction, signatureData);
//        // change the RLP encode of V in signature
//        fields.set(fields.size() - 3, RlpString.create(headerByte));
//        byte[] signedTx = RlpEncoder.encode(new RlpList(fields));
        List<RlpType> fields = TransactionEncoder.asRlpValues(rawTransaction, signatureData);
        byte[] signedTx = RlpEncoder.encode(new RlpList(
                new RlpList(fields.subList(0, 6)),			// [nonce, gasPrice, gas, to, value, data]
                RlpString.create(signatureData.getV()[0] - 27),	// adjusted V
                fields.get(fields.size() - 2),				// R
                fields.get(fields.size() - 1)));			// S

        return  signedTx;
    }

    public static byte[] signMessage(
            RawTransaction rawTransaction, long chainId, Credentials credentials) {
        byte[] encodedTransaction = encode(rawTransaction, chainId);
        Sign.SignatureData signatureData =
                Sign.signMessage(encodedTransaction, credentials.getEcKeyPair());

        Sign.SignatureData eip155SignatureData = createEip155SignatureData(signatureData, chainId);
        return getEncodedData(rawTransaction, eip155SignatureData);
    }

    @Deprecated
    public static byte[] signMessage(
            RawTransaction rawTransaction, byte chainId, Credentials credentials) {
        return signMessage(rawTransaction, (long) chainId, credentials);
    }

    public static Sign.SignatureData createEip155SignatureData(
            Sign.SignatureData signatureData, long chainId) {
        BigInteger v = Numeric.toBigInt(signatureData.getV());
        v = v.subtract(BigInteger.valueOf(LOWER_REAL_V));
        v = v.add(BigInteger.valueOf(chainId * 2));
        v = v.add(BigInteger.valueOf(CHAIN_ID_INC));

        return new Sign.SignatureData(v.toByteArray(), signatureData.getR(), signatureData.getS());
    }

    @Deprecated
    public static Sign.SignatureData createEip155SignatureData(
            Sign.SignatureData signatureData, byte chainId) {
        return createEip155SignatureData(signatureData, (long) chainId);
    }

    public static byte[] encode(RawTransaction rawTransaction) {
        return encode(rawTransaction, null);
    }

    public static byte[] encode(RawTransaction rawTransaction, long chainId) {
        Sign.SignatureData signatureData =
                new Sign.SignatureData(longToBytes(chainId), new byte[] {}, new byte[] {});
        return encode(rawTransaction, signatureData);
    }

    @Deprecated
    public static byte[] encode(RawTransaction rawTransaction, byte chainId) {
        return encode(rawTransaction, (long) chainId);
    }

    private static byte[] encode(RawTransaction rawTransaction, Sign.SignatureData signatureData) {
        List<RlpType> values = asRlpValues(rawTransaction, signatureData);
        RlpList rlpList = new RlpList(values);
        return RlpEncoder.encode(rlpList);
    }

    private static byte[] longToBytes(long x) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(x);
        return buffer.array();
    }

    public static List<RlpType> asRlpValues(
            RawTransaction rawTransaction, Sign.SignatureData signatureData) {
        List<RlpType> result = new ArrayList<>();

        result.add(RlpString.create(rawTransaction.getNonce()));
        result.add(RlpString.create(rawTransaction.getGasPrice()));
        result.add(RlpString.create(rawTransaction.getGasLimit()));

        // an empty to address (contract creation) should not be encoded as a numeric 0 value
        String to = rawTransaction.getTo();
        if (to != null && to.length() > 0) {
            // addresses that start with zeros should be encoded with the zeros included, not
            // as numeric values
            result.add(RlpString.create(Numeric.hexStringToByteArray(to)));
        } else {
            result.add(RlpString.create(""));
        }

        result.add(RlpString.create(rawTransaction.getValue()));

        // value field will already be hex encoded, so we need to convert into binary first
        byte[] data = Numeric.hexStringToByteArray(rawTransaction.getData());
        result.add(RlpString.create(data));

        if (signatureData != null) {
            result.add(RlpString.create(Bytes.trimLeadingZeroes(signatureData.getV())));
            result.add(RlpString.create(Bytes.trimLeadingZeroes(signatureData.getR())));
            result.add(RlpString.create(Bytes.trimLeadingZeroes(signatureData.getS())));
        }

        return result;
    }
}

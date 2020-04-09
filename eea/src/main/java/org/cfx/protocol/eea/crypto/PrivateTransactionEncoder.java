package org.cfx.protocol.eea.crypto;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.cfx.crypto.Credentials;
import org.cfx.crypto.Sign;
import org.cfx.crypto.TransactionEncoder;
import org.cfx.rlp.RlpEncoder;
import org.cfx.rlp.RlpList;
import org.cfx.rlp.RlpString;
import org.cfx.rlp.RlpType;
import org.cfx.utils.Base64String;

/** Create signed RLP encoded private transaction. */
public class PrivateTransactionEncoder {

    public static byte[] signMessage(
            final RawPrivateTransaction rawTransaction, final Credentials credentials) {
        final byte[] encodedTransaction = encode(rawTransaction);
        final Sign.SignatureData signatureData =
                Sign.signMessage(encodedTransaction, credentials.getEcKeyPair());

        return encode(rawTransaction, signatureData);
    }

    public static byte[] signMessage(
            final RawPrivateTransaction rawTransaction,
            final long chainId,
            final Credentials credentials) {
        final byte[] encodedTransaction = encode(rawTransaction, chainId);
        final Sign.SignatureData signatureData =
                Sign.signMessage(encodedTransaction, credentials.getEcKeyPair());

        final Sign.SignatureData eip155SignatureData =
                TransactionEncoder.createEip155SignatureData(signatureData, chainId);
        return encode(rawTransaction, eip155SignatureData);
    }

    public static byte[] encode(final RawPrivateTransaction rawTransaction) {
        return encode(rawTransaction, null);
    }

    public static byte[] encode(final RawPrivateTransaction rawTransaction, final long chainId) {
        final Sign.SignatureData signatureData =
                new Sign.SignatureData(longToBytes(chainId), new byte[] {}, new byte[] {});
        return encode(rawTransaction, signatureData);
    }

    private static byte[] encode(
            final RawPrivateTransaction rawTransaction, final Sign.SignatureData signatureData) {
        final List<RlpType> values = asRlpValues(rawTransaction, signatureData);
        final RlpList rlpList = new RlpList(values);
        return RlpEncoder.encode(rlpList);
    }

    private static byte[] longToBytes(long x) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(x);
        return buffer.array();
    }

    public static List<RlpType> asRlpValues(
            final RawPrivateTransaction privateTransaction,
            final Sign.SignatureData signatureData) {

        final List<RlpType> result =
                new ArrayList<>(
                        TransactionEncoder.asRlpValues(
                                privateTransaction.asRawTransaction(), signatureData));

        result.add(privateTransaction.getPrivateFrom().asRlp());

        privateTransaction
                .getPrivateFor()
                .ifPresent(privateFor -> result.add(Base64String.unwrapListToRlp(privateFor)));

        privateTransaction.getPrivacyGroupId().map(Base64String::asRlp).ifPresent(result::add);

        result.add(RlpString.create(privateTransaction.getRestriction().getRestriction()));

        return result;
    }
}

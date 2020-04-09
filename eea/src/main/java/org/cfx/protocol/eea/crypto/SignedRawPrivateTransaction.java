package org.cfx.protocol.eea.crypto;

import java.math.BigInteger;
import java.util.List;

import org.cfx.crypto.Sign;
import org.cfx.crypto.SignatureDataOperations;
import org.cfx.crypto.SignedRawTransaction;
import org.cfx.utils.Base64String;
import org.cfx.utils.Restriction;

public class SignedRawPrivateTransaction extends RawPrivateTransaction
        implements SignatureDataOperations {

    private final Sign.SignatureData signatureData;

    public SignedRawPrivateTransaction(
            final BigInteger nonce,
            final BigInteger gasPrice,
            final BigInteger gasLimit,
            final String to,
            final String data,
            final Sign.SignatureData signatureData,
            final Base64String privateFrom,
            final List<Base64String> privateFor,
            final Base64String privacyGroupId,
            final Restriction restriction) {
        super(
                nonce,
                gasPrice,
                gasLimit,
                to,
                data,
                privateFrom,
                privateFor,
                privacyGroupId,
                restriction);
        this.signatureData = signatureData;
    }

    public SignedRawPrivateTransaction(
            final SignedRawTransaction signedRawTransaction,
            final Base64String privateFrom,
            final List<Base64String> privateFor,
            final Restriction restriction) {
        this(signedRawTransaction, privateFrom, privateFor, null, restriction);
    }

    public SignedRawPrivateTransaction(
            final SignedRawTransaction signedRawTransaction,
            final Base64String privateFrom,
            final Base64String privacyGroupId,
            final Restriction restriction) {
        this(signedRawTransaction, privateFrom, null, privacyGroupId, restriction);
    }

    private SignedRawPrivateTransaction(
            final SignedRawTransaction signedRawTransaction,
            final Base64String privateFrom,
            final List<Base64String> privateFor,
            final Base64String privacyGroupId,
            final Restriction restriction) {
        this(
                signedRawTransaction.getNonce(),
                signedRawTransaction.getGasPrice(),
                signedRawTransaction.getGasLimit(),
                signedRawTransaction.getTo(),
                signedRawTransaction.getData(),
                signedRawTransaction.getSignatureData(),
                privateFrom,
                privateFor,
                privacyGroupId,
                restriction);
    }

    public Sign.SignatureData getSignatureData() {
        return signatureData;
    }

    @Override
    public byte[] getEncodedTransaction(Long chainId) {
        if (null == chainId) {
            return PrivateTransactionEncoder.encode(this);
        } else {
            return PrivateTransactionEncoder.encode(this, chainId);
        }
    }
}

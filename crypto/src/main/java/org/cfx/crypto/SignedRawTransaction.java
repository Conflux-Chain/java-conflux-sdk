package org.cfx.crypto;

import java.math.BigInteger;

public class SignedRawTransaction extends RawTransaction implements SignatureDataOperations {

    private final Sign.SignatureData signatureData;

    public SignedRawTransaction(
            BigInteger nonce,
            BigInteger gasPrice,
            BigInteger gasLimit,
            String to,
            BigInteger value,
            String data,
            Sign.SignatureData signatureData) {
        super(nonce, gasPrice, gasLimit, to, value, data);
        this.signatureData = signatureData;
    }

    public Sign.SignatureData getSignatureData() {
        return signatureData;
    }

    @Override
    public byte[] getEncodedTransaction(Long chainId) {
        if (null == chainId) {
            return TransactionEncoder.encode(this);
        } else {
            return TransactionEncoder.encode(this, chainId);
        }
    }
}

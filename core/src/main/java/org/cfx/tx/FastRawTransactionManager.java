package org.cfx.tx;

import java.io.IOException;
import java.math.BigInteger;

import org.cfx.crypto.Credentials;
import org.cfx.protocol.Cfx;
import org.cfx.tx.response.TransactionReceiptProcessor;

/**
 * Simple RawTransactionManager derivative that manages nonces to facilitate multiple transactions
 * per block.
 */
public class FastRawTransactionManager extends RawTransactionManager {

    private volatile BigInteger nonce = BigInteger.valueOf(-1);

    public FastRawTransactionManager(Cfx cfx, Credentials credentials, byte chainId) {
        super(cfx, credentials, chainId);
    }

    public FastRawTransactionManager(Cfx cfx, Credentials credentials) {
        super(cfx, credentials);
    }

    public FastRawTransactionManager(
            Cfx cfx,
            Credentials credentials,
            TransactionReceiptProcessor transactionReceiptProcessor) {
        super(cfx, credentials, ChainId.NONE, transactionReceiptProcessor);
    }

    public FastRawTransactionManager(
            Cfx cfx,
            Credentials credentials,
            byte chainId,
            TransactionReceiptProcessor transactionReceiptProcessor) {
        super(cfx, credentials, chainId, transactionReceiptProcessor);
    }

    @Override
    protected synchronized BigInteger getNonce() throws IOException {
        if (nonce.signum() == -1) {
            // obtain lock
            nonce = super.getNonce();
        } else {
            nonce = nonce.add(BigInteger.ONE);
        }
        return nonce;
    }

    public BigInteger getCurrentNonce() {
        return nonce;
    }

    public synchronized void resetNonce() throws IOException {
        nonce = super.getNonce();
    }

    public synchronized void setNonce(BigInteger value) {
        nonce = value;
    }
}

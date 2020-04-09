package org.cfx.tx.response;

import java.io.IOException;

import org.cfx.protocol.Cfx;
import org.cfx.protocol.core.methods.response.TransactionReceipt;
import org.cfx.protocol.exceptions.TransactionException;

/**
 * Return an {@link EmptyTransactionReceipt} receipt back to callers containing only the transaction
 * hash.
 */
public class NoOpProcessor extends TransactionReceiptProcessor {

    public NoOpProcessor(Cfx cfx) {
        super(cfx);
    }

    @Override
    public TransactionReceipt waitForTransactionReceipt(String transactionHash)
            throws IOException, TransactionException {
        return new EmptyTransactionReceipt(transactionHash);
    }
}

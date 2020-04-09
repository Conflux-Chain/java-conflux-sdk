package org.cfx.tx.response;

import java.io.IOException;
import java.util.Optional;

import org.cfx.protocol.Cfx;
import org.cfx.protocol.core.methods.response.CfxGetTransactionReceipt;
import org.cfx.protocol.core.methods.response.TransactionReceipt;
import org.cfx.protocol.exceptions.TransactionException;

/** Abstraction for managing how we wait for transaction receipts to be generated on the network. */
public abstract class TransactionReceiptProcessor {

    private final Cfx cfx;

    public TransactionReceiptProcessor(Cfx cfx) {
        this.cfx = cfx;
    }

    public abstract TransactionReceipt waitForTransactionReceipt(String transactionHash)
            throws IOException, TransactionException;

    Optional<? extends TransactionReceipt> sendTransactionReceiptRequest(String transactionHash)
            throws IOException, TransactionException {
        CfxGetTransactionReceipt transactionReceipt =
                cfx.cfxGetTransactionReceipt(transactionHash).send();
        if (transactionReceipt.hasError()) {
            throw new TransactionException(
                    "Error processing request: " + transactionReceipt.getError().getMessage());
        }

        return transactionReceipt.getTransactionReceipt();
    }
}

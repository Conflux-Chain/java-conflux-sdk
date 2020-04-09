package org.cfx.tx.response;

import org.cfx.protocol.core.methods.response.TransactionReceipt;

/** Transaction receipt processor callback. */
public interface Callback {
    void accept(TransactionReceipt transactionReceipt);

    void exception(Exception exception);
}

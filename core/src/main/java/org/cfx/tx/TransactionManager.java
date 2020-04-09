package org.cfx.tx;

import java.io.IOException;
import java.math.BigInteger;

import org.cfx.protocol.Cfx;
import org.cfx.protocol.core.DefaultBlockParameter;
import org.cfx.protocol.core.methods.response.CfxGetCode;
import org.cfx.protocol.core.methods.response.CfxSendTransaction;
import org.cfx.protocol.core.methods.response.TransactionReceipt;
import org.cfx.protocol.exceptions.TransactionException;
import org.cfx.tx.response.PollingTransactionReceiptProcessor;
import org.cfx.tx.response.TransactionReceiptProcessor;

import static org.cfx.protocol.core.JsonRpc2_0Cfx.DEFAULT_BLOCK_TIME;

/**
 * Transaction manager abstraction for executing transactions with conflux client via various
 * mechanisms.
 */
public abstract class TransactionManager {

    public static final int DEFAULT_POLLING_ATTEMPTS_PER_TX_HASH = 40;
    public static final long DEFAULT_POLLING_FREQUENCY = DEFAULT_BLOCK_TIME;

    private final TransactionReceiptProcessor transactionReceiptProcessor;
    private final String fromAddress;

    protected TransactionManager(
            TransactionReceiptProcessor transactionReceiptProcessor, String fromAddress) {
        this.transactionReceiptProcessor = transactionReceiptProcessor;
        this.fromAddress = fromAddress;
    }

    protected TransactionManager(Cfx cfx, String fromAddress) {

        this(
                new PollingTransactionReceiptProcessor(
                        cfx, DEFAULT_POLLING_FREQUENCY, DEFAULT_POLLING_ATTEMPTS_PER_TX_HASH),
                fromAddress);
    }

    protected TransactionManager(
            Cfx cfx, int attempts, long sleepDuration, String fromAddress) {
        this(new PollingTransactionReceiptProcessor(cfx, sleepDuration, attempts), fromAddress);
    }

    protected TransactionReceipt executeTransaction(
            BigInteger gasPrice, BigInteger gasLimit, String to, String data, BigInteger value)
            throws IOException, TransactionException {

        return executeTransaction(gasPrice, gasLimit, to, data, value, false);
    }

    protected TransactionReceipt executeTransaction(
            BigInteger gasPrice,
            BigInteger gasLimit,
            String to,
            String data,
            BigInteger value,
            boolean constructor)
            throws IOException, TransactionException {

        CfxSendTransaction ethSendTransaction =
                sendTransaction(gasPrice, gasLimit, to, data, value, constructor);
        return processResponse(ethSendTransaction);
    }

    public CfxSendTransaction sendTransaction(
            BigInteger gasPrice, BigInteger gasLimit, String to, String data, BigInteger value)
            throws IOException {
        return sendTransaction(gasPrice, gasLimit, to, data, value, false);
    }

    public abstract CfxSendTransaction sendTransaction(
            BigInteger gasPrice,
            BigInteger gasLimit,
            String to,
            String data,
            BigInteger value,
            boolean constructor)
            throws IOException;

    public abstract String sendCall(
            String to, String data, DefaultBlockParameter defaultBlockParameter) throws IOException;

    public abstract CfxGetCode getCode(
            String contractAddress, DefaultBlockParameter defaultBlockParameter) throws IOException;

    public String getFromAddress() {
        return fromAddress;
    }

    private TransactionReceipt processResponse(CfxSendTransaction transactionResponse)
            throws IOException, TransactionException {
        if (transactionResponse.hasError()) {
            throw new RuntimeException(
                    "Error processing transaction request: "
                            + transactionResponse.getError().getMessage());
        }

        String transactionHash = transactionResponse.getTransactionHash();

        return transactionReceiptProcessor.waitForTransactionReceipt(transactionHash);
    }
}

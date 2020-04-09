package org.cfx.tx;

import java.io.IOException;
import java.math.BigInteger;

import org.cfx.protocol.Cfx;
import org.cfx.protocol.core.DefaultBlockParameter;
import org.cfx.protocol.core.methods.request.Transaction;
import org.cfx.protocol.core.methods.response.CfxGetCode;
import org.cfx.protocol.core.methods.response.CfxSendTransaction;
import org.cfx.tx.response.TransactionReceiptProcessor;


public class ClientTransactionManager extends org.cfx.tx.TransactionManager {

    private final Cfx cfx;

    public ClientTransactionManager(Cfx cfx, String fromAddress) {
        super(cfx, fromAddress);
        this.cfx = cfx;
    }

    public ClientTransactionManager(
            Cfx cfx, String fromAddress, int attempts, int sleepDuration) {
        super(cfx, attempts, sleepDuration, fromAddress);
        this.cfx = cfx;
    }

    public ClientTransactionManager(
            Cfx cfx,
            String fromAddress,
            TransactionReceiptProcessor transactionReceiptProcessor) {
        super(transactionReceiptProcessor, fromAddress);
        this.cfx = cfx;
    }

    @Override
    public CfxSendTransaction sendTransaction(
            BigInteger gasPrice,
            BigInteger gasLimit,
            String to,
            String data,
            BigInteger value,
            boolean constructor)
            throws IOException {

        Transaction transaction =
                new Transaction(getFromAddress(), null, gasPrice, gasLimit, to, value, data);

        return cfx.cfxSendTransaction(transaction).send();
    }

    @Override
    public String sendCall(String to, String data, DefaultBlockParameter defaultBlockParameter)
            throws IOException {
        return cfx.cfxCall(
                        Transaction.createCfxCallTransaction(getFromAddress(), to, data),
                        defaultBlockParameter)
                .send()
                .getValue();
    }

    @Override
    public CfxGetCode getCode(
            final String contractAddress, final DefaultBlockParameter defaultBlockParameter)
            throws IOException {
        return cfx.cfxGetCode(contractAddress, defaultBlockParameter).send();
    }
}

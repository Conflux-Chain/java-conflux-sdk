package org.cfx.tx;

import java.io.IOException;
import java.math.BigInteger;

import org.cfx.protocol.Cfx;
import org.cfx.protocol.core.DefaultBlockParameter;
import org.cfx.protocol.core.methods.request.Transaction;
import org.cfx.protocol.core.methods.response.CfxGetCode;
import org.cfx.protocol.core.methods.response.CfxSendTransaction;

/** Transaction manager implementation for read-only operations on smart contracts. */
public class ReadonlyTransactionManager extends TransactionManager {
    private final Cfx cfx;
    private String fromAddress;

    public ReadonlyTransactionManager(Cfx cfx, String fromAddress) {
        super(cfx, fromAddress);
        this.cfx = cfx;
        this.fromAddress = fromAddress;
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
        throw new UnsupportedOperationException(
                "Only read operations are supported by this transaction manager");
    }

    @Override
    public String sendCall(String to, String data, DefaultBlockParameter defaultBlockParameter)
            throws IOException {
        return cfx.cfxCall(
                        Transaction.createCfxCallTransaction(fromAddress, to, data),
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

package org.cfx.tx;

import java.io.IOException;
import java.math.BigInteger;

import org.cfx.crypto.Credentials;
import org.cfx.crypto.Hash;
import org.cfx.crypto.RawTransaction;
import org.cfx.crypto.TransactionEncoder;
import org.cfx.protocol.Cfx;
import org.cfx.protocol.core.DefaultBlockParameter;
import org.cfx.protocol.core.DefaultBlockParameterName;
import org.cfx.protocol.core.methods.request.Transaction;
import org.cfx.protocol.core.methods.response.CfxGetCode;
import org.cfx.protocol.core.methods.response.CfxGetTransactionCount;
import org.cfx.protocol.core.methods.response.CfxSendTransaction;
import org.cfx.tx.exceptions.TxHashMismatchException;
import org.cfx.tx.response.TransactionReceiptProcessor;
import org.cfx.utils.Numeric;
import org.cfx.utils.TxHashVerifier;

public class RawTransactionManager extends TransactionManager {

    private final Cfx cfx;
    final Credentials credentials;

    private final long chainId;

    protected TxHashVerifier txHashVerifier = new TxHashVerifier();

    public RawTransactionManager(Cfx cfx, Credentials credentials, long chainId) {
        super(cfx, credentials.getAddress());

        this.cfx = cfx;
        this.credentials = credentials;

        this.chainId = chainId;
    }

    public RawTransactionManager(
            Cfx cfx,
            Credentials credentials,
            long chainId,
            TransactionReceiptProcessor transactionReceiptProcessor) {
        super(transactionReceiptProcessor, credentials.getAddress());

        this.cfx = cfx;
        this.credentials = credentials;

        this.chainId = chainId;
    }

    public RawTransactionManager(
            Cfx cfx, Credentials credentials, long chainId, int attempts, long sleepDuration) {
        super(cfx, attempts, sleepDuration, credentials.getAddress());

        this.cfx = cfx;
        this.credentials = credentials;

        this.chainId = chainId;
    }

    public RawTransactionManager(Cfx cfx, Credentials credentials) {
        this(cfx, credentials, ChainId.NONE);
    }

    public RawTransactionManager(
            Cfx cfx, Credentials credentials, int attempts, int sleepDuration) {
        this(cfx, credentials, ChainId.NONE, attempts, sleepDuration);
    }

    protected BigInteger getNonce() throws IOException {
        CfxGetTransactionCount ethGetTransactionCount =
                cfx.cfxGetTransactionCount(
                                credentials.getAddress(), DefaultBlockParameterName.PENDING)
                        .send();

        return ethGetTransactionCount.getTransactionCount();
    }

    public TxHashVerifier getTxHashVerifier() {
        return txHashVerifier;
    }

    public void setTxHashVerifier(TxHashVerifier txHashVerifier) {
        this.txHashVerifier = txHashVerifier;
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

        BigInteger nonce = getNonce();

        RawTransaction rawTransaction =
                RawTransaction.createTransaction(nonce, gasPrice, gasLimit, to, value, data);

        return signAndSend(rawTransaction);
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

    /*
     * @param rawTransaction a RawTransaction istance to be signed
     * @return The transaction signed and encoded without ever broadcasting it
     */
    public String sign(RawTransaction rawTransaction) {

        byte[] signedMessage;

        if (chainId > ChainId.NONE) {
            signedMessage = TransactionEncoder.signMessage(rawTransaction, chainId, credentials);
        } else {
            signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
        }

        return Numeric.toHexString(signedMessage);
    }

    public CfxSendTransaction signAndSend(RawTransaction rawTransaction) throws IOException {
        String hexValue = sign(rawTransaction);
        CfxSendTransaction ethSendTransaction = cfx.cfxSendRawTransaction(hexValue).send();

        if (ethSendTransaction != null && !ethSendTransaction.hasError()) {
            String txHashLocal = Hash.sha3(hexValue);
            String txHashRemote = ethSendTransaction.getTransactionHash();
            if (!txHashVerifier.verify(txHashLocal, txHashRemote)) {
                throw new TxHashMismatchException(txHashLocal, txHashRemote);
            }
        }

        return ethSendTransaction;
    }
}

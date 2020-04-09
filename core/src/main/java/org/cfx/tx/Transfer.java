package org.cfx.tx;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.cfx.crypto.Credentials;
import org.cfx.protocol.Cfx;
import org.cfx.protocol.core.RemoteCall;
import org.cfx.protocol.core.methods.response.TransactionReceipt;
import org.cfx.protocol.exceptions.TransactionException;
import org.cfx.utils.Convert;
import org.cfx.utils.Numeric;

/** Class for performing cfx transactions on the Conflux blockchain. */
public class Transfer extends ManagedTransaction {

    // This is the cost to send Ether between parties
    public static final BigInteger GAS_LIMIT = BigInteger.valueOf(21000);

    public Transfer(Cfx cfx, TransactionManager transactionManager) {
        super(cfx, transactionManager);
    }

    /**
     * Given the duration required to execute a transaction, asyncronous execution is strongly
     * recommended via {@link Transfer#sendFunds(String, BigDecimal, Convert.Unit)}.
     *
     * @param toAddress destination address
     * @param value amount to send
     * @param unit of specified send
     * @return {@link Optional} containing our transaction receipt
     * @throws ExecutionException if the computation threw an exception
     * @throws InterruptedException if the current thread was interrupted while waiting
     * @throws TransactionException if the transaction was not mined while waiting
     */
    private TransactionReceipt send(String toAddress, BigDecimal value, Convert.Unit unit)
            throws IOException, InterruptedException, TransactionException {

        BigInteger gasPrice = requestCurrentGasPrice();
        return send(toAddress, value, unit, gasPrice, GAS_LIMIT);
    }

    private TransactionReceipt send(
            String toAddress,
            BigDecimal value,
            Convert.Unit unit,
            BigInteger gasPrice,
            BigInteger gasLimit)
            throws IOException, InterruptedException, TransactionException {

        BigDecimal weiValue = Convert.toWei(value, unit);
        if (!Numeric.isIntegerValue(weiValue)) {
            throw new UnsupportedOperationException(
                    "Non decimal Wei value provided: "
                            + value
                            + " "
                            + unit.toString()
                            + " = "
                            + weiValue
                            + " Wei");
        }

        String resolvedAddress = ensResolver.resolve(toAddress);
        return send(resolvedAddress, "", weiValue.toBigIntegerExact(), gasPrice, gasLimit);
    }

    public static RemoteCall<TransactionReceipt> sendFunds(
            Cfx cfx,
            Credentials credentials,
            String toAddress,
            BigDecimal value,
            Convert.Unit unit)
            throws InterruptedException, IOException, TransactionException {

        TransactionManager transactionManager = new RawTransactionManager(cfx, credentials);

        return new RemoteCall<>(
                () -> new Transfer(cfx, transactionManager).send(toAddress, value, unit));
    }

    /**
     * Execute the provided function as a transaction asynchronously. This is intended for one-off
     * fund transfers. For multiple, create an instance.
     *
     * @param toAddress destination address
     * @param value amount to send
     * @param unit of specified send
     * @return {@link RemoteCall} containing executing transaction
     */
    public RemoteCall<TransactionReceipt> sendFunds(
            String toAddress, BigDecimal value, Convert.Unit unit) {
        return new RemoteCall<>(() -> send(toAddress, value, unit));
    }

    public RemoteCall<TransactionReceipt> sendFunds(
            String toAddress,
            BigDecimal value,
            Convert.Unit unit,
            BigInteger gasPrice,
            BigInteger gasLimit) {
        return new RemoteCall<>(() -> send(toAddress, value, unit, gasPrice, gasLimit));
    }
}

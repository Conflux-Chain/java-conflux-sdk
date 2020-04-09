package org.cfx.contracts.token;

import java.math.BigInteger;
import java.util.List;

import io.reactivex.Flowable;

import org.cfx.protocol.core.DefaultBlockParameter;
import org.cfx.protocol.core.RemoteCall;
import org.cfx.protocol.core.methods.response.TransactionReceipt;

public interface ERC20BasicInterface<T> {

    RemoteCall<BigInteger> totalSupply();

    RemoteCall<BigInteger> balanceOf(String who);

    RemoteCall<TransactionReceipt> transfer(String to, BigInteger value);

    List<T> getTransferEvents(TransactionReceipt transactionReceipt);

    Flowable<T> transferEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock);
}

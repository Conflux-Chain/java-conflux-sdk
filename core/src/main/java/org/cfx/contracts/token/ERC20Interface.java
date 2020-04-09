package org.cfx.contracts.token;

import java.math.BigInteger;
import java.util.List;

import io.reactivex.Flowable;

import org.cfx.protocol.core.DefaultBlockParameter;
import org.cfx.protocol.core.RemoteCall;
import org.cfx.protocol.core.methods.response.TransactionReceipt;

public interface ERC20Interface<R, T> extends ERC20BasicInterface<T> {

    RemoteCall<BigInteger> allowance(String owner, String spender);

    RemoteCall<TransactionReceipt> approve(String spender, BigInteger value);

    RemoteCall<TransactionReceipt> transferFrom(String from, String to, BigInteger value);

    List<R> getApprovalEvents(TransactionReceipt transactionReceipt);

    Flowable<R> approvalEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock);
}

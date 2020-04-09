package org.cfx.contracts.eip165.generated;

import java.math.BigInteger;
import java.util.Arrays;

import org.cfx.abi.datatypes.generated.Bytes4;
import org.cfx.abi.TypeReference;
import org.cfx.abi.datatypes.Bool;
import org.cfx.abi.datatypes.Function;
import org.cfx.abi.datatypes.Type;
import org.cfx.crypto.Credentials;
import org.cfx.protocol.Cfx;
import org.cfx.protocol.core.RemoteCall;
import org.cfx.tx.Contract;
import org.cfx.tx.TransactionManager;
import org.cfx.tx.gas.ContractGasProvider;


public class ERC165 extends Contract {
    private static final String BINARY = "Bin file was not provided";

    public static final String FUNC_SUPPORTSINTERFACE = "supportsInterface";

    @Deprecated
    protected ERC165(String contractAddress, Cfx cfx, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, cfx, credentials, gasPrice, gasLimit);
    }

    protected ERC165(String contractAddress, Cfx cfx, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, cfx, credentials, contractGasProvider);
    }

    @Deprecated
    protected ERC165(String contractAddress, Cfx cfx, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, cfx, transactionManager, gasPrice, gasLimit);
    }

    protected ERC165(String contractAddress, Cfx cfx, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, cfx, transactionManager, contractGasProvider);
    }

    public RemoteCall<Boolean> supportsInterface(byte[] interfaceID) {
        final Function function = new Function(FUNC_SUPPORTSINTERFACE, 
                Arrays.<Type>asList(new Bytes4(interfaceID)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    @Deprecated
    public static ERC165 load(String contractAddress, Cfx cfx, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new ERC165(contractAddress, cfx, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static ERC165 load(String contractAddress, Cfx cfx, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new ERC165(contractAddress, cfx, transactionManager, gasPrice, gasLimit);
    }

    public static ERC165 load(String contractAddress, Cfx cfx, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new ERC165(contractAddress, cfx, credentials, contractGasProvider);
    }

    public static ERC165 load(String contractAddress, Cfx cfx, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new ERC165(contractAddress, cfx, transactionManager, contractGasProvider);
    }
}

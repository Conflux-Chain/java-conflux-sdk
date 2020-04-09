package org.cfx.contracts.eip721.generated;

import java.math.BigInteger;
import java.util.Arrays;

import org.cfx.abi.datatypes.Address;
import org.cfx.abi.TypeReference;
import org.cfx.abi.datatypes.Function;
import org.cfx.abi.datatypes.Type;
import org.cfx.abi.datatypes.generated.Uint256;
import org.cfx.crypto.Credentials;
import org.cfx.protocol.Cfx;
import org.cfx.protocol.core.RemoteCall;
import org.cfx.tx.Contract;
import org.cfx.tx.TransactionManager;
import org.cfx.tx.gas.ContractGasProvider;

public class ERC721Enumerable extends Contract {
    private static final String BINARY = "Bin file was not provided";

    public static final String FUNC_TOTALSUPPLY = "totalSupply";

    public static final String FUNC_TOKENOFOWNERBYINDEX = "tokenOfOwnerByIndex";

    public static final String FUNC_TOKENBYINDEX = "tokenByIndex";

    @Deprecated
    protected ERC721Enumerable(String contractAddress, Cfx cfx, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, cfx, credentials, gasPrice, gasLimit);
    }

    protected ERC721Enumerable(String contractAddress, Cfx cfx, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, cfx, credentials, contractGasProvider);
    }

    @Deprecated
    protected ERC721Enumerable(String contractAddress, Cfx cfx, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, cfx, transactionManager, gasPrice, gasLimit);
    }

    protected ERC721Enumerable(String contractAddress, Cfx cfx, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, cfx, transactionManager, contractGasProvider);
    }

    public RemoteCall<BigInteger> totalSupply() {
        final Function function = new Function(FUNC_TOTALSUPPLY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> tokenOfOwnerByIndex(String _owner, BigInteger _index) {
        final Function function = new Function(FUNC_TOKENOFOWNERBYINDEX, 
                Arrays.<Type>asList(new Address(_owner),
                new Uint256(_index)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> tokenByIndex(BigInteger _index) {
        final Function function = new Function(FUNC_TOKENBYINDEX, 
                Arrays.<Type>asList(new Uint256(_index)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    @Deprecated
    public static ERC721Enumerable load(String contractAddress, Cfx cfx, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new ERC721Enumerable(contractAddress, cfx, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static ERC721Enumerable load(String contractAddress, Cfx cfx, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new ERC721Enumerable(contractAddress, cfx, transactionManager, gasPrice, gasLimit);
    }

    public static ERC721Enumerable load(String contractAddress, Cfx cfx, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new ERC721Enumerable(contractAddress, cfx, credentials, contractGasProvider);
    }

    public static ERC721Enumerable load(String contractAddress, Cfx cfx, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new ERC721Enumerable(contractAddress, cfx, transactionManager, contractGasProvider);
    }
}

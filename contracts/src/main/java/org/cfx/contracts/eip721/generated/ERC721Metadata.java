package org.cfx.contracts.eip721.generated;

import java.math.BigInteger;
import java.util.Arrays;

import org.cfx.abi.datatypes.generated.Uint256;
import org.cfx.abi.TypeReference;
import org.cfx.abi.datatypes.Function;
import org.cfx.abi.datatypes.Type;
import org.cfx.abi.datatypes.Utf8String;
import org.cfx.crypto.Credentials;
import org.cfx.protocol.Cfx;
import org.cfx.protocol.core.RemoteCall;
import org.cfx.tx.Contract;
import org.cfx.tx.TransactionManager;
import org.cfx.tx.gas.ContractGasProvider;

public class ERC721Metadata extends Contract {
    private static final String BINARY = "Bin file was not provided";

    public static final String FUNC_NAME = "name";

    public static final String FUNC_SYMBOL = "symbol";

    public static final String FUNC_TOKENURI = "tokenURI";

    @Deprecated
    protected ERC721Metadata(String contractAddress, Cfx cfx, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, cfx, credentials, gasPrice, gasLimit);
    }

    protected ERC721Metadata(String contractAddress, Cfx cfx, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, cfx, credentials, contractGasProvider);
    }

    @Deprecated
    protected ERC721Metadata(String contractAddress, Cfx cfx, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, cfx, transactionManager, gasPrice, gasLimit);
    }

    protected ERC721Metadata(String contractAddress, Cfx cfx, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, cfx, transactionManager, contractGasProvider);
    }

    public RemoteCall<String> name() {
        final Function function = new Function(FUNC_NAME, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> symbol() {
        final Function function = new Function(FUNC_SYMBOL, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> tokenURI(BigInteger _tokenId) {
        final Function function = new Function(FUNC_TOKENURI, 
                Arrays.<Type>asList(new Uint256(_tokenId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    @Deprecated
    public static ERC721Metadata load(String contractAddress, Cfx cfx, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new ERC721Metadata(contractAddress, cfx, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static ERC721Metadata load(String contractAddress, Cfx cfx, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new ERC721Metadata(contractAddress, cfx, transactionManager, gasPrice, gasLimit);
    }

    public static ERC721Metadata load(String contractAddress, Cfx cfx, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new ERC721Metadata(contractAddress, cfx, credentials, contractGasProvider);
    }

    public static ERC721Metadata load(String contractAddress, Cfx cfx, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new ERC721Metadata(contractAddress, cfx, transactionManager, contractGasProvider);
    }
}

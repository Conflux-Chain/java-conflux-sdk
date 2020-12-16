package conflux.web3j.contract;

import conflux.web3j.Account;
import conflux.web3j.Cfx;
import conflux.web3j.RpcException;
import conflux.web3j.contract.abi.DecodeUtil;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.Uint256;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

public class ERC777 extends ContractCall {
    private Account account;
    private String contract;

    private static final TypeReference<DynamicArray<Address>> TYPE_DYNAMIC_ARRAY_ADDRESS = new TypeReference<DynamicArray<Address>>() {};

    public ERC777(Cfx cfx, String erc777Address) {
        super(cfx, erc777Address);
    }

    public ERC777(Cfx cfx, String address, Account account) {
        super(cfx, address);
        this.account = account;
        this.contract = address;
    }

    public String name() throws RpcException {
        return this.callAndGet(Utf8String.class, "name");
    }

    public String symbol() throws RpcException {
        return this.callAndGet(Utf8String.class, "symbol");
    }

    public BigInteger granularity() throws RpcException {
        return this.callAndGet(Uint256.class, "granularity");
    }

    public BigInteger totalSupply() throws RpcException {
        return this.callAndGet(Uint256.class, "totalSupply");
    }

    public BigInteger balanceOf(String owner) throws RpcException {
        return this.callAndGet(Uint256.class, "balanceOf", new Address(owner));
    }

    public boolean isOperatorFor(String operator, String tokenHolder) throws RpcException {
        return this.callAndGet(Bool.class, "isOperatorFor", new Address(operator), new Address(tokenHolder));
    }

    public List<String> defaultOperators() throws RpcException {
        String encodedResult = this.call("defaultOperators").sendAndGet();
        List<Address> operators = DecodeUtil.decode(encodedResult, TYPE_DYNAMIC_ARRAY_ADDRESS);

        return operators.stream()
                .map(address -> address.getValue())
                .collect(Collectors.toList());
    }

    public String send(Account.Option option, String recipient, BigInteger amount, byte[] data) throws Exception {
        return this.account.call(option, this.contract, "send", new Address(recipient), new Uint256(amount), new DynamicBytes(data));
    }

    public String burn(Account.Option option, BigInteger amount, byte[] data) throws Exception {
        return this.account.call(option, this.contract, "burn", new Uint256(amount), new DynamicBytes(data));
    }

    public String authorizeOperator(Account.Option option, String operator) throws Exception {
        return this.account.call(option, this.contract, "authorizeOperator", new Address(operator));
    }

    public String revokeOperator(Account.Option option, String operator) throws Exception {
        return this.account.call(option, this.contract, "revokeOperator", new Address(operator));
    }

    public String operatorSend(Account.Option option, String sender, String recipient, BigInteger amount, byte[] data, byte[] operatorData) throws Exception {
        return this.account.call(option, this.contract, "operatorSend", new Address(sender), new Address(recipient), new Uint256(amount), new DynamicBytes(data), new DynamicBytes(operatorData));
    }

    public String operatorBurn(Account.Option option, String account, BigInteger amount, byte[] data, byte[] operatorData) throws Exception {
        return this.account.call(option, this.contract, "operatorBurn", new Address(account), new Uint256(amount), new DynamicBytes(data), new DynamicBytes(operatorData));
    }
}

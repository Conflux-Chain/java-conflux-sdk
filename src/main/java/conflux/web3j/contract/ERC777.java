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
        String encodedResult = this.call("name").sendAndGet();
        return DecodeUtil.decode(encodedResult, Utf8String.class);
    }

    public String symbol() throws RpcException {
        String encodedResult = this.call("symbol").sendAndGet();
        return DecodeUtil.decode(encodedResult, Utf8String.class);
    }

    public BigInteger granularity() throws RpcException {
        String encodedResult = this.call("granularity").sendAndGet();
        return DecodeUtil.decode(encodedResult, Uint256.class);
    }

    public BigInteger totalSupply() throws RpcException {
        String encodedResult = this.call("totalSupply").sendAndGet();
        return DecodeUtil.decode(encodedResult, Uint256.class);
    }

    public BigInteger balanceOf(String owner) throws RpcException {
        String encodedResult = this.call("balanceOf", new Address(owner)).sendAndGet();
        return DecodeUtil.decode(encodedResult, Uint256.class);
    }

    public boolean isOperatorFor(String operator, String tokenHolder) throws RpcException {
        String encodedResult = this.call("isOperatorFor", new Address(operator), new Address(tokenHolder)).sendAndGet();
        return DecodeUtil.decode(encodedResult, Bool.class);
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

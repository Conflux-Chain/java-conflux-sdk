package conflux.web3j.contract;

import conflux.web3j.Account;
import conflux.web3j.Cfx;
import conflux.web3j.RpcException;
import conflux.web3j.contract.abi.DecodeUtil;
import conflux.web3j.types.CfxAddress;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Address;

import java.math.BigInteger;
import java.util.List;

public class ERC777 extends ContractCall {
    private Account account;
    private CfxAddress contract;

    private static final TypeReference<DynamicArray<Address>> TYPE_DYNAMIC_ARRAY_ADDRESS = new TypeReference<DynamicArray<Address>>() {};

    public ERC777(Cfx cfx, CfxAddress erc777Address) {
        super(cfx, erc777Address);
    }

    public ERC777(Cfx cfx, CfxAddress address, Account account) {
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

    public BigInteger balanceOf(Address owner) throws RpcException {
        return this.callAndGet(Uint256.class, "balanceOf", owner);
    }

    public BigInteger balanceOf(CfxAddress owner) throws RpcException {
        return this.balanceOf(owner.getABIAddress());
    }

    public BigInteger balanceOf(String owner) throws RpcException {
        return this.balanceOf(new Address(owner));
    }

    public boolean isOperatorFor(Address operator, Address tokenHolder) throws RpcException {
        return this.callAndGet(Bool.class, "isOperatorFor", operator, tokenHolder);
    }

    public boolean isOperatorFor(String operator, String tokenHolder) throws RpcException {
        return this.isOperatorFor(new Address(operator), new Address(tokenHolder));
    }

    public boolean isOperatorFor(CfxAddress operator, CfxAddress tokenHolder) throws RpcException {
        return this.isOperatorFor(operator.getABIAddress(), tokenHolder.getABIAddress());
    }

    public List<Address> defaultOperators() throws RpcException {
        String encodedResult = this.call("defaultOperators").sendAndGet();
        return DecodeUtil.decode(encodedResult, TYPE_DYNAMIC_ARRAY_ADDRESS);
    }

    public String send(Account.Option option, Address recipient, BigInteger amount, byte[] data) throws Exception {
        return this.account.call(option, this.contract, "send", recipient, new Uint256(amount), new DynamicBytes(data));
    }

    public String send(Account.Option option, CfxAddress recipient, BigInteger amount, byte[] data) throws Exception {
        return this.send(option, recipient.getABIAddress(), amount, data);
    }

    public String send(Account.Option option, String recipient, BigInteger amount, byte[] data) throws Exception {
        return this.send(option, new Address(recipient), amount, data);
    }

    public String burn(Account.Option option, BigInteger amount, byte[] data) throws Exception {
        return this.account.call(option, this.contract, "burn", new Uint256(amount), new DynamicBytes(data));
    }

    public String authorizeOperator(Account.Option option, Address operator) throws Exception {
        return this.account.call(option, this.contract, "authorizeOperator", operator);
    }

    public String authorizeOperator(Account.Option option, CfxAddress operator) throws Exception {
        return this.authorizeOperator(option, operator.getABIAddress());
    }

    public String authorizeOperator(Account.Option option, String operator) throws Exception {
        return this.authorizeOperator(option, new Address(operator));
    }

    public String revokeOperator(Account.Option option, Address operator) throws Exception {
        return this.account.call(option, this.contract, "revokeOperator", operator);
    }

    public String revokeOperator(Account.Option option, CfxAddress operator) throws Exception {
        return this.revokeOperator(option, operator.getABIAddress());
    }

    public String revokeOperator(Account.Option option, String operator) throws Exception {
        return this.revokeOperator(option, new Address(operator));
    }

    public String operatorSend(Account.Option option, Address sender, Address recipient, BigInteger amount, byte[] data, byte[] operatorData) throws Exception {
        return this.account.call(option, this.contract, "operatorSend", sender, recipient, new Uint256(amount), new DynamicBytes(data), new DynamicBytes(operatorData));
    }

    public String operatorSend(Account.Option option, CfxAddress sender, CfxAddress recipient, BigInteger amount, byte[] data, byte[] operatorData) throws Exception {
        return this.operatorSend(option, sender.getABIAddress(), recipient.getABIAddress(), amount, data, operatorData);
    }

    public String operatorSend(Account.Option option, String sender, String recipient, BigInteger amount, byte[] data, byte[] operatorData) throws Exception {
        return this.operatorSend(option, new Address(sender), new Address(recipient), amount, data, operatorData);
    }

    public String operatorBurn(Account.Option option, Address account, BigInteger amount, byte[] data, byte[] operatorData) throws Exception {
        return this.account.call(option, this.contract, "operatorBurn", account, new Uint256(amount), new DynamicBytes(data), new DynamicBytes(operatorData));
    }

    public String operatorBurn(Account.Option option, CfxAddress account, BigInteger amount, byte[] data, byte[] operatorData) throws Exception {
        return this.operatorBurn(option, account.getABIAddress(), amount, data, operatorData);
    }

    public String operatorBurn(Account.Option option, String account, BigInteger amount, byte[] data, byte[] operatorData) throws Exception {
        return this.operatorBurn(option, new Address(account), amount, data, operatorData);
    }
}

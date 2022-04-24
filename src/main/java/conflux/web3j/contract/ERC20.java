package conflux.web3j.contract;

import java.math.BigInteger;

import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Utf8String;

import conflux.web3j.Account;
import conflux.web3j.Cfx;
import conflux.web3j.RpcException;
import conflux.web3j.types.CfxAddress;
import org.web3j.abi.datatypes.generated.Uint8;

public class ERC20 extends ContractCall{
    private Account account;
    private CfxAddress contract;

    public ERC20(Cfx cfx, CfxAddress address) {
        super(cfx, address);
    }

    public ERC20(Cfx cfx, CfxAddress address, Account account) {
        super(cfx, address);
        this.account = account;
        this.contract = address;
    }

    public BigInteger totalSupply() throws RpcException {
        return this.callAndGet(Uint256.class, "totalSupply");
    }

    public String name() throws RpcException {
        return this.callAndGet(Utf8String.class, "name");
    }

    public String symbol() throws RpcException {
        return this.callAndGet(Utf8String.class, "symbol");
    }

    public BigInteger decimals() throws RpcException {
        return this.callAndGet(Uint8.class, "decimals");
    }

    public BigInteger balanceOf(Address account) throws RpcException {
        return this.callAndGet(Uint256.class, "balanceOf", account);
    }

    public BigInteger balanceOf(CfxAddress account) throws RpcException {
        return  this.balanceOf(account.getABIAddress());
    }

    public BigInteger balanceOf(String account) throws RpcException {
        return  this.balanceOf(new Address(account));
    }

    public BigInteger allowance(Address owner, Address spender) throws RpcException {
        return this.callAndGet(Uint256. class, "allowance", owner, spender);
    }

    public BigInteger allowance(CfxAddress owner, CfxAddress spender) throws RpcException {
        return this.allowance(owner.getABIAddress(), spender.getABIAddress());
    }

    public BigInteger allowance(String owner, String spender) throws RpcException {
        return this.allowance(new Address(owner), new Address(spender));
    }

    public String transfer(Account.Option option, Address recipient, BigInteger amount) throws Exception {
        return this.account.call(option, this.contract, "transfer", recipient, new Uint256(amount));
    }

    public String transfer(Account.Option option, CfxAddress recipient, BigInteger amount) throws Exception {
        return this.transfer(option, recipient.getABIAddress(), amount);
    }

    public String transfer(Account.Option option, String recipient, BigInteger amount) throws Exception {
        return this.transfer(option, new Address(recipient), amount);
    }

    public String approve(Account.Option option, Address spender, BigInteger amount) throws Exception {
        return this.account.call(option, this.contract, "approve", spender, new Uint256(amount));
    }

    public String approve(Account.Option option, CfxAddress spender, BigInteger amount) throws Exception {
        return this.approve(option, spender.getABIAddress(), amount);
    }

    public String approve(Account.Option option, String spender, BigInteger amount) throws Exception {
        return this.approve(option, new Address(spender), amount);
    }

    public String transferFrom(Account.Option option, Address sender, Address recipient, BigInteger amount) throws Exception {
        return this.account.call(option, this.contract, "transferFrom", sender, recipient, new Uint256(amount));
    }

    public String transferFrom(Account.Option option, CfxAddress sender, CfxAddress recipient, BigInteger amount) throws Exception {
        return this.transferFrom(option, sender.getABIAddress(), recipient.getABIAddress(), amount);
    }

    public String transferFrom(Account.Option option, String sender, String recipient, BigInteger amount) throws Exception {
        return this.transferFrom(option, new Address(sender), new Address(recipient), amount);
    }
}

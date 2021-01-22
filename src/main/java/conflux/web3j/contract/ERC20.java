package conflux.web3j.contract;

import java.math.BigInteger;

import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.generated.Uint256;

import conflux.web3j.Account;
import conflux.web3j.Cfx;
import conflux.web3j.RpcException;

public class ERC20 extends ContractCall{
    private Account account;
    private String contract;

    public ERC20(Cfx cfx, String address) {
        super(cfx, address);
    }

    public ERC20(Cfx cfx, String address, Account account) {
        super(cfx, address);
        this.account = account;
        this.contract = address;
    }

    public BigInteger totalSupply() throws RpcException {
        return this.callAndGet(Uint256.class, "totalSupply");
    }

    public BigInteger balanceOf(String account) throws RpcException {
        return this.callAndGet(Uint256.class, "balanceOf", new Address(account));
    }

    public BigInteger allowance(String owner, String spender) throws RpcException {
        return this.callAndGet(Uint256. class, "allowance", new Address(owner), new Address(spender));
    }

    public String transfer(Account.Option option, String recipient, BigInteger amount) throws Exception {
        return this.account.call(option, this.contract, "transfer", new Address(recipient), new Uint256(amount));
    }

    public String approve(Account.Option option, String spender, BigInteger amount) throws Exception {
        return this.account.call(option, this.contract, "approve", new Address(spender), new Uint256(amount));
    }

    public String transferFrom(Account.Option option, String sender, String recipient, BigInteger amount) throws Exception {
        return this.account.call(option, this.contract, "transferFrom", new Address(sender), new Address(recipient), new Uint256(amount));
    }
}

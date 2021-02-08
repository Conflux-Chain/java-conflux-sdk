package conflux.web3j.contract;

import java.math.BigInteger;

import org.web3j.abi.datatypes.generated.Uint256;

import conflux.web3j.Account;
import conflux.web3j.types.Address;
import conflux.web3j.Cfx;
import conflux.web3j.RpcException;

public class ERC20 extends ContractCall{
    private Account account;
    private Address contract;

    public ERC20(Cfx cfx, Address address) {
        super(cfx, address);
    }

    public ERC20(Cfx cfx, Address address, Account account) {
        super(cfx, address);
        this.account = account;
        this.contract = address;
    }

    public BigInteger totalSupply() throws RpcException {
        return this.callAndGet(Uint256.class, "totalSupply");
    }

    public BigInteger balanceOf(Address account) throws RpcException {
        return this.callAndGet(Uint256.class, "balanceOf", account.getABIAddress());
    }

    public BigInteger allowance(Address owner, Address spender) throws RpcException {
        return this.callAndGet(Uint256. class, "allowance", owner.getABIAddress(), spender.getABIAddress());
    }

    public String transfer(Account.Option option, Address recipient, BigInteger amount) throws Exception {
        return this.account.call(option, this.contract, "transfer", recipient.getABIAddress(), new Uint256(amount));
    }

    public String approve(Account.Option option, Address spender, BigInteger amount) throws Exception {
        return this.account.call(option, this.contract, "approve", spender.getABIAddress(), new Uint256(amount));
    }

    public String transferFrom(Account.Option option, Address sender, Address recipient, BigInteger amount) throws Exception {
        return this.account.call(option, this.contract, "transferFrom", sender.getABIAddress(), recipient.getABIAddress(), new Uint256(amount));
    }
}

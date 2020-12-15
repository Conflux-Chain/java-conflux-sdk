package conflux.web3j.contract;

import conflux.web3j.Account;
import conflux.web3j.Cfx;
import conflux.web3j.RpcException;
import conflux.web3j.contract.abi.DecodeUtil;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.generated.Uint256;

import java.math.BigInteger;

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
        String encodedResult = this.call("totalSupply").sendAndGet();
        return DecodeUtil.decode(encodedResult, Uint256.class);
    }

    public BigInteger balanceOf(String account) throws RpcException {
        String encodedResult = this.call("balanceOf", new Address(account)).sendAndGet();
        return DecodeUtil.decode(encodedResult, Uint256.class);
    }

    public BigInteger allowance(String owner, String spender) throws RpcException {
        String encodedResult = this.call("allowance", new Address(owner), new Address(spender)).sendAndGet();
        return DecodeUtil.decode(encodedResult, Uint256.class);
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

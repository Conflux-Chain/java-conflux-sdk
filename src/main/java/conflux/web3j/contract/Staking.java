package conflux.web3j.contract;

import conflux.web3j.Account;
import conflux.web3j.Cfx;
import conflux.web3j.RpcException;
import conflux.web3j.contract.abi.DecodeUtil;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.generated.Uint256;

import java.math.BigInteger;

public class Staking extends ContractCall {
    static String contract = "0x0888000000000000000000000000000000000002";
    private Account account;

    public Staking(Account account) {
        super(account.getCfx(), Staking.contract);
        this.account = account;
    }

    public Staking(Cfx cfx) {
        super(cfx, Staking.contract);
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public BigInteger getStakingBalance(String address) throws RpcException {
        String encodedResult = this.call("getStakingBalance", new Address(address)).sendAndGet();
        return DecodeUtil.decode(encodedResult, Uint256.class);
    }

    public BigInteger getLockedStakingBalance(String address, BigInteger blockNumber) throws RpcException {
        String encodedResult = this.call("getLockedStakingBalance", new Address(address), new Uint256(blockNumber)).sendAndGet();
        return DecodeUtil.decode(encodedResult, Uint256.class);
    }

    public BigInteger getVotePower(String address, BigInteger blockNumber) throws RpcException {
        String encodedResult = this.call("getVotePower", new Address(address), new Uint256(blockNumber)).sendAndGet();
        return DecodeUtil.decode(encodedResult, Uint256.class);
    }

    public void deposit(Account.Option option, BigInteger amount) throws Exception {
        account.call(option, contract, "deposit", new Uint256(amount));
    }

    public void voteLock(Account.Option option, BigInteger amount, BigInteger unlockBlockNumber) throws Exception {
        account.call(option, contract, "voteLock", new Uint256(amount), new Uint256(unlockBlockNumber));
    }

    public void withdraw(Account.Option option, BigInteger amount) throws Exception {
        account.call(option, contract, "withdraw", new Uint256(amount));
    }
}

package conflux.web3j.contract.internals;

import conflux.web3j.Account;
import conflux.web3j.Cfx;
import conflux.web3j.RpcException;
import conflux.web3j.contract.ContractCall;
import conflux.web3j.contract.abi.DecodeUtil;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.generated.Uint256;

import java.math.BigInteger;

public class Staking extends ContractCall {
    private final static String contract = "0x0888000000000000000000000000000000000002";
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

    public BigInteger getStakingBalance(String user) throws RpcException {
        return this.callAndGet(Uint256.class, "getStakingBalance", new Address(user));
    }

    public BigInteger getLockedStakingBalance(String user, BigInteger blockNumber) throws RpcException {
        return this.callAndGet(Uint256.class,"getLockedStakingBalance", new Address(user), new Uint256(blockNumber));
    }

    public BigInteger getVotePower(String user, BigInteger blockNumber) throws RpcException {
        return this.callAndGet(Uint256.class, "getVotePower", new Address(user), new Uint256(blockNumber));
    }

    public String deposit(Account.Option option, BigInteger amount) throws Exception {
        return account.call(option, contract, "deposit", new Uint256(amount));
    }

    public String voteLock(Account.Option option, BigInteger amount, BigInteger unlockBlockNumber) throws Exception {
        return account.call(option, contract, "voteLock", new Uint256(amount), new Uint256(unlockBlockNumber));
    }

    public String withdraw(Account.Option option, BigInteger amount) throws Exception {
        return account.call(option, contract, "withdraw", new Uint256(amount));
    }
}

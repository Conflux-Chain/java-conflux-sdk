package conflux.web3j.contract.internals;

import java.math.BigInteger;

import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.Address;

import conflux.web3j.Account;
import conflux.web3j.Cfx;
import conflux.web3j.RpcException;
import conflux.web3j.contract.ContractCall;
import conflux.web3j.types.CfxAddress;

public class Staking extends ContractCall {
    private final static String contract = "0x0888000000000000000000000000000000000002";
    private Account account;
    private final CfxAddress contractAddress;

    public Staking(Account account, int networkId) {
        super(account.getCfx(), new CfxAddress(Staking.contract, networkId));
        this.contractAddress = new CfxAddress(Staking.contract, networkId);
        this.account = account;
    }

    public Staking(Cfx cfx) {
        super(cfx, new CfxAddress(Staking.contract, cfx.getIntNetworkId()));
        this.contractAddress = new CfxAddress(Staking.contract, cfx.getIntNetworkId());
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public BigInteger getStakingBalance(Address user) throws RpcException {
        return this.callAndGet(Uint256.class, "getStakingBalance", user);
    }

    public BigInteger getLockedStakingBalance(Address user, BigInteger blockNumber) throws RpcException {
        return this.callAndGet(Uint256.class,"getLockedStakingBalance", user, new Uint256(blockNumber));
    }

    public BigInteger getVotePower(Address user, BigInteger blockNumber) throws RpcException {
        return this.callAndGet(Uint256.class, "getVotePower", user, new Uint256(blockNumber));
    }

    public String deposit(Account.Option option, BigInteger amount) throws Exception {
        return account.call(option, this.contractAddress, "deposit", new Uint256(amount));
    }

    public String voteLock(Account.Option option, BigInteger amount, BigInteger unlockBlockNumber) throws Exception {
        return account.call(option, this.contractAddress, "voteLock", new Uint256(amount), new Uint256(unlockBlockNumber));
    }

    public String withdraw(Account.Option option, BigInteger amount) throws Exception {
        return account.call(option, this.contractAddress, "withdraw", new Uint256(amount));
    }
}

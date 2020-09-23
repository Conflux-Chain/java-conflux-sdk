package conflux.web3j.contract;

import conflux.web3j.Account;
import org.web3j.abi.datatypes.generated.Uint256;

import java.math.BigInteger;

public class Staking {
    static String contract = "0x0888000000000000000000000000000000000002";
    private Account account;

    public Staking(Account account) {
        this.account = account;
    }

    public void deposit(Account.Option option, BigInteger amount) throws Exception {
        account.call(option, contract, "deposit", new Uint256(amount));
    }

    public void voteLock(Account.Option option, BigInteger amount, BigInteger unlockBlockNumber) throws Exception {
        account.call(option, contract, "vote_lock", new Uint256(amount), new Uint256(unlockBlockNumber));
    }

    public void withdraw(Account.Option option, BigInteger amount) throws Exception {
        account.call(option, contract, "withdraw", new Uint256(amount));
    }
}

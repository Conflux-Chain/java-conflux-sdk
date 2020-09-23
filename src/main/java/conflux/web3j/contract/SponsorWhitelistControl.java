package conflux.web3j.contract;

import conflux.web3j.Account;

import java.math.BigInteger;

import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.generated.Uint256;

public class SponsorWhitelistControl {
    static String contract = "0x0888000000000000000000000000000000000001";
    private Account account;

    public SponsorWhitelistControl(Account account) {
        this.account = account;
    }

    public void addPrivilege(Account.Option option, Address[] addresses) throws Exception {
        account.call(option, contract, "add_privilege", addresses);
    }

    public void removePrivilege(Account.Option option, Address[] addresses) throws Exception {
        account.call(option, contract, "remove_privilege", addresses);
    }

    public void setSponsorForCollateral(Account.Option option, String address) throws Exception {
        account.call(option, contract, "set_sponsor_for_collateral", new Address(address));
    }

    public void setSponsorForGas(Account.Option option, String address, BigInteger upperBound) throws Exception {
        account.call(option, contract, "set_sponsor_for_gas", new Address(address), new Uint256(upperBound));
    }
}

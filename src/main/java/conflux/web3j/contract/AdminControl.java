package conflux.web3j.contract;

import conflux.web3j.Account;
import conflux.web3j.Account.Option;
import org.web3j.abi.datatypes.Address;

public class AdminControl {
    static String contract = "0x0888000000000000000000000000000000000000";
    private Account account;

    public AdminControl(Account account) {
        this.account = account;
    }

    public void destroy (Option option, String contractAddress) throws Exception {
        this.account.call(option, contract, "destroy", new Address(contractAddress));
    }

    public void setAdmin(Option option, String contractAddress, String admin) throws Exception {
        this.account.call(option, contract, "set_admin", new Address(contractAddress), new Address(admin));
    }
}

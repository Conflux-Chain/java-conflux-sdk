package conflux.web3j.contract;

import conflux.web3j.Account;
import conflux.web3j.Account.Option;
import conflux.web3j.RpcException;
import conflux.web3j.contract.abi.DecodeUtil;
import org.web3j.abi.datatypes.Address;
import conflux.web3j.Cfx;

public class AdminControl extends ContractCall {
    static String contract = "0x0888000000000000000000000000000000000000";
    private Account account;  // if account not set, can only use getAdmin method

    public AdminControl(Account account) {
        super(account.getCfx(), contract);
        this.account = account;
    }

    public AdminControl(Cfx cfx) {
        super(cfx, this.contract);
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getAdmin(String address) throws RpcException {
        String encodedResult = this.call("getAdmin", new Address(address)).sendAndGet();
        return DecodeUtil.decode(encodedResult, Address.class);
    }

    public void destroy (Option option, String contractAddress) throws Exception {
        this.account.call(option, contract, "destroy", new Address(contractAddress));
    }

    public void setAdmin(Option option, String contractAddress, String admin) throws Exception {
        this.account.call(option, contract, "setAdmin", new Address(contractAddress), new Address(admin));
    }
}

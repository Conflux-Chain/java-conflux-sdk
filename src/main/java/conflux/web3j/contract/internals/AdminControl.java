package conflux.web3j.contract.internals;

import conflux.web3j.Account;
import conflux.web3j.Account.Option;
import conflux.web3j.RpcException;
import conflux.web3j.contract.ContractCall;
import conflux.web3j.contract.abi.DecodeUtil;
import org.web3j.abi.datatypes.Address;
import conflux.web3j.Cfx;

public class AdminControl extends ContractCall {
    private final static String contract = "0x0888000000000000000000000000000000000000";
    private Account account;  // if account not set, can only use getAdmin method

    public AdminControl(Account account) {
        super(account.getCfx(), AdminControl.contract);
        this.account = account;
    }

    public AdminControl(Cfx cfx) {
        super(cfx, AdminControl.contract);
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getAdmin(String contractAddr) throws RpcException {
        return this.callAndGet(Address.class, "getAdmin", new Address(contractAddr));
    }

    public String destroy (Option option, String contractAddr) throws Exception {
        String admin = getAdmin(contractAddr);
        if (!admin.equalsIgnoreCase(account.getAddress())) {
            throw new Exception("Administrator privilege required");
        }
        return this.account.call(option, contract, "destroy", new Address(contractAddr));
    }

    public String setAdmin(Option option, String contractAddr, String newAdmin) throws Exception {
        String admin = getAdmin(contractAddr);
        if (!admin.equalsIgnoreCase(account.getAddress())) {
            throw new Exception("Administrator privilege required");
        }
        return this.account.call(option, contract, "setAdmin", new Address(contractAddr), new Address(newAdmin));
    }
}

package conflux.web3j.contract.internals;

import conflux.web3j.types.Address;
import conflux.web3j.Account;
import conflux.web3j.Account.Option;
import conflux.web3j.Cfx;
import conflux.web3j.RpcException;
import conflux.web3j.contract.ContractCall;

public class AdminControl extends ContractCall {
    private final static String contract = "0x0888000000000000000000000000000000000000";
    private Account account;  // if account not set, can only use getAdmin method
    private Address contractAddress;

    public AdminControl(Account account, int networkId) {
        super(account.getCfx(), new Address(AdminControl.contract, networkId));
        this.account = account;
        this.contractAddress = new Address(AdminControl.contract, networkId);
    }

    public AdminControl(Cfx cfx) {
        super(cfx, new Address(AdminControl.contract, cfx.getIntNetworkId()));
        this.contractAddress = new Address(AdminControl.contract, cfx.getIntNetworkId());
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getAdmin(Address contractAddr) throws RpcException {
        return this.callAndGet(Address.class, "getAdmin", contractAddr.getABIAddress());
    }

    public String destroy (Option option, Address contractAddr) throws Exception {
        String admin = getAdmin(contractAddr);
        if (!admin.equalsIgnoreCase(account.getAddress().getHexAddress())) {
            throw new Exception("Administrator privilege required");
        }
        return this.account.call(option, this.contractAddress, "destroy", contractAddr.getABIAddress());
    }

    public String setAdmin(Option option, Address contractAddr, Address newAdmin) throws Exception {
        String admin = getAdmin(contractAddr);
        if (!admin.equalsIgnoreCase(account.getAddress().getHexAddress())) {
            throw new Exception("Administrator privilege required");
        }
        return this.account.call(option, this.contractAddress, "setAdmin", contractAddr.getABIAddress(), newAdmin.getABIAddress());
    }
}

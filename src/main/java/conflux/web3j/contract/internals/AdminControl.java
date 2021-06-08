package conflux.web3j.contract.internals;

import conflux.web3j.Account;
import conflux.web3j.Account.Option;
import conflux.web3j.Cfx;
import conflux.web3j.RpcException;
import conflux.web3j.contract.ContractCall;
import org.web3j.abi.datatypes.Address;
import conflux.web3j.types.CfxAddress;

public class AdminControl extends ContractCall {
    private final static String contract = "0x0888000000000000000000000000000000000000";
    private Account account;  // if account not set, can only use getAdmin method
    private CfxAddress contractAddress;

    public AdminControl(Account account) {
        super(account.getCfx(), new CfxAddress(AdminControl.contract, account.getCfx().getIntNetworkId()));
        this.contractAddress = new CfxAddress(AdminControl.contract, account.getCfx().getIntNetworkId());
        this.account = account;
    }

    public AdminControl(Cfx cfx) {
        super(cfx, new CfxAddress(AdminControl.contract, cfx.getIntNetworkId()));
        this.contractAddress = new CfxAddress(AdminControl.contract, cfx.getIntNetworkId());
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Address getAdmin(Address contractAddr) throws RpcException {
        String hexAddress = this.callAndGet(Address.class, "getAdmin", contractAddr);
        return new Address(hexAddress);
    }

    public String destroy (Option option, Address contractAddr) throws Exception {
        Address admin = getAdmin(contractAddr);
        if (!admin.getValue().equalsIgnoreCase(account.getAddress().getHexAddress())) {
            throw new Exception("Administrator privilege required");
        }
        return this.account.call(option, this.contractAddress, "destroy", contractAddr);
    }

    public String setAdmin(Option option, Address contractAddr, Address newAdmin) throws Exception {
        Address admin = getAdmin(contractAddr);
        if (!admin.getValue().equalsIgnoreCase(account.getAddress().getHexAddress())) {
            throw new Exception("Administrator privilege required");
        }
        return this.account.call(option, this.contractAddress, "setAdmin", contractAddr, newAdmin);
    }
}

package conflux.web3j.contract.internals;

import java.math.BigInteger;

import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.Address;

import conflux.web3j.Account;
import conflux.web3j.Cfx;
import conflux.web3j.RpcException;
import conflux.web3j.contract.ContractCall;


public class SponsorWhitelistControl extends ContractCall {
    private final static String contract = "0x0888000000000000000000000000000000000001";
    private Account account;
    private final conflux.web3j.types.Address contractAddress;

    public SponsorWhitelistControl(Account account, int networkId) {
        super(account.getCfx(), new conflux.web3j.types.Address(SponsorWhitelistControl.contract, networkId));
        this.contractAddress = new conflux.web3j.types.Address(SponsorWhitelistControl.contract, networkId);
        this.account = account;
    }

    public SponsorWhitelistControl(Cfx cfx) {
        super(cfx, new conflux.web3j.types.Address(SponsorWhitelistControl.contract, cfx.getIntNetworkId()));
        this.contractAddress = new conflux.web3j.types.Address(SponsorWhitelistControl.contract, cfx.getIntNetworkId());
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Address getSponsorForGas(Address contractAddr) throws RpcException {
        String hexAddress = this.callAndGet(Address.class, "getSponsorForGas", contractAddr);
        return new Address(hexAddress);
    }

    public BigInteger getSponsoredBalanceForGas(Address contractAddr) throws RpcException {
        return this.callAndGet(Uint256.class, "getSponsoredBalanceForGas", contractAddr);
    }

    public BigInteger getSponsoredGasFeeUpperBound(Address contractAddr) throws RpcException {
        return this.callAndGet(Uint256.class, "getSponsoredGasFeeUpperBound", contractAddr);
    }

    public Address getSponsorForCollateral(Address contractAddr) throws RpcException {
        String hexAddress = this.callAndGet(Address.class, "getSponsorForCollateral", contractAddr);
        return new Address(hexAddress);
    }

    public BigInteger getSponsoredBalanceForCollateral(Address contractAddr) throws RpcException {
        return this.callAndGet(Uint256.class, "getSponsoredBalanceForCollateral", contractAddr);
    }

    public boolean isWhitelisted(Address contractAddr, Address user) throws RpcException {
        return this.callAndGet(Bool.class, "isWhitelisted", contractAddr, contractAddr);
    }

    public boolean isAllWhitelisted(Address contractAddr) throws RpcException {
        return this.callAndGet(Bool.class, "isAllWhitelisted", contractAddr);
    }

//    public void addPrivilege(Account.Option option, String[] addresses) throws Exception {
//        List<Address> list = Arrays.stream(addresses).map(a -> new Address(a)).collect(Collectors.toList());
//        account.call(option, contract, "addPrivilege", new DynamicArray<Address>(Address.class, list));
//    }
//
//    public void removePrivilege(Account.Option option, String[] addresses) throws Exception {
//        List<Address> list = Arrays.stream(addresses).map(a -> new Address(a)).collect(Collectors.toList());
//        account.call(option, contract, "removePrivilege", new DynamicArray<Address>(Address.class, list));
//    }

    public String setSponsorForCollateral(Account.Option option, Address contractAddr) throws Exception {
        return account.call(option, this.contractAddress, "setSponsorForCollateral", contractAddr);
    }

    public String setSponsorForGas(Account.Option option, Address contractAddr, BigInteger upperBound) throws Exception {
        return account.call(option, this.contractAddress, "setSponsorForGas", contractAddr, new Uint256(upperBound));
    }

    public String addPrivilegeByAdmin(Account.Option option, Address contractAddr, Address[] addresses) throws Exception {
        return account.call(option, this.contractAddress, "addPrivilegeByAdmin", contractAddr, new DynamicArray<>(Address.class, addresses));
    }

    public String removePrivilegeByAdmin(Account.Option option, Address contractAddr, Address[] addresses) throws Exception {
        return account.call(option, this.contractAddress, "removePrivilegeByAdmin", contractAddr, new DynamicArray<>(Address.class, addresses));
    }
}

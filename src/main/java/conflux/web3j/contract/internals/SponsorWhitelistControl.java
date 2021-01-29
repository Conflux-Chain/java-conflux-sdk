package conflux.web3j.contract.internals;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.generated.Uint256;

import conflux.web3j.Account;
import conflux.web3j.Cfx;
import conflux.web3j.types.Address;
import conflux.web3j.RpcException;
import conflux.web3j.contract.ContractCall;

public class SponsorWhitelistControl extends ContractCall {
    private final static String contract = "0x0888000000000000000000000000000000000001";
    private Account account;
    private Address contractAddress;

    public SponsorWhitelistControl(Account account, int networkId) {
        super(account.getCfx(), new Address(SponsorWhitelistControl.contract, networkId));
        this.account = account;
        this.contractAddress = new Address(SponsorWhitelistControl.contract, networkId);
    }

    public SponsorWhitelistControl(Cfx cfx) {
        super(cfx, new Address(SponsorWhitelistControl.contract, cfx.getIntNetworkId()));
        this.contractAddress = new Address(SponsorWhitelistControl.contract, cfx.getIntNetworkId());
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getSponsorForGas(Address contractAddr) throws RpcException {
        return this.callAndGet(Address.class, "getSponsorForGas", contractAddr.getABIAddress());
    }

    public BigInteger getSponsoredBalanceForGas(Address contractAddr) throws RpcException {
        return this.callAndGet(Uint256.class, "getSponsoredBalanceForGas", contractAddr.getABIAddress());
    }

    public BigInteger getSponsoredGasFeeUpperBound(Address contractAddr) throws RpcException {
        return this.callAndGet(Uint256.class, "getSponsoredGasFeeUpperBound", contractAddr.getABIAddress());
    }

    public String getSponsorForCollateral(Address contractAddr) throws RpcException {
        return this.callAndGet(Address.class, "getSponsorForCollateral", contractAddr.getABIAddress());
    }

    public BigInteger getSponsoredBalanceForCollateral(Address contractAddr) throws RpcException {
        return this.callAndGet(Uint256.class, "getSponsoredBalanceForCollateral", contractAddr.getABIAddress());
    }

    public boolean isWhitelisted(Address contractAddr, Address user) throws RpcException {
        return this.callAndGet(Bool.class, "isWhitelisted", contractAddr.getABIAddress(), contractAddr.getABIAddress());
    }

    public boolean isAllWhitelisted(Address contractAddr) throws RpcException {
        return this.callAndGet(Bool.class, "isAllWhitelisted", contractAddr.getABIAddress());
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
        return account.call(option, this.contractAddress, "setSponsorForCollateral", contractAddr.getABIAddress());
    }

    public String setSponsorForGas(Account.Option option, Address contractAddr, BigInteger upperBound) throws Exception {
        return account.call(option, this.contractAddress, "setSponsorForGas", contractAddr.getABIAddress(), new Uint256(upperBound));
    }

    public String addPrivilegeByAdmin(Account.Option option, Address contractAddr, Address[] address) throws Exception {
        List<org.web3j.abi.datatypes.Address> list = Arrays.stream(address).map(Address::getABIAddress).collect(Collectors.toList());
        return account.call(option, this.contractAddress, "addPrivilegeByAdmin", contractAddr.getABIAddress(),
                new DynamicArray<>(org.web3j.abi.datatypes.Address.class, list));
    }

    public String removePrivilegeByAdmin(Account.Option option, Address contractAddr, Address[] address) throws Exception {
        List<org.web3j.abi.datatypes.Address> list = Arrays.stream(address).map(Address::getABIAddress).collect(Collectors.toList());
        return account.call(option, this.contractAddress, "removePrivilegeByAdmin", contractAddr,
                new DynamicArray<>(org.web3j.abi.datatypes.Address.class, list));
    }
}

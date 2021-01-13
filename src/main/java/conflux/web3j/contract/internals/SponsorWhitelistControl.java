package conflux.web3j.contract.internals;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.generated.Uint256;

import conflux.web3j.Account;
import conflux.web3j.Cfx;
import conflux.web3j.RpcException;
import conflux.web3j.contract.ContractCall;

public class SponsorWhitelistControl extends ContractCall {
    private final static String contract = "0x0888000000000000000000000000000000000001";
    private Account account;

    public SponsorWhitelistControl(Account account) {
        super(account.getCfx(), SponsorWhitelistControl.contract);
        this.account = account;
    }

    public SponsorWhitelistControl(Cfx cfx) {
        super(cfx, SponsorWhitelistControl.contract);
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getSponsorForGas(String contractAddr) throws RpcException {
        return this.callAndGet(Address.class, "getSponsorForGas", new Address(contractAddr));
    }

    public BigInteger getSponsoredBalanceForGas(String contractAddr) throws RpcException {
        return this.callAndGet(Uint256.class, "getSponsoredBalanceForGas", new Address(contractAddr));
    }

    public BigInteger getSponsoredGasFeeUpperBound(String contractAddr) throws RpcException {
        return this.callAndGet(Uint256.class, "getSponsoredGasFeeUpperBound", new Address(contractAddr));
    }

    public String getSponsorForCollateral(String contractAddr) throws RpcException {
        return this.callAndGet(Address.class, "getSponsorForCollateral", new Address(contractAddr));
    }

    public BigInteger getSponsoredBalanceForCollateral(String contractAddr) throws RpcException {
        return this.callAndGet(Uint256.class, "getSponsoredBalanceForCollateral", new Address(contractAddr));
    }

    public boolean isWhitelisted(String contractAddr, String user) throws RpcException {
        return this.callAndGet(Bool.class, "isWhitelisted", new Address(contractAddr), new Address(user));
    }

    public boolean isAllWhitelisted(String contractAddr) throws RpcException {
        return this.callAndGet(Bool.class, "isAllWhitelisted", new Address(contractAddr));
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

    public String setSponsorForCollateral(Account.Option option, String contractAddr) throws Exception {
        return account.call(option, contract, "setSponsorForCollateral", new Address(contractAddr));
    }

    public String setSponsorForGas(Account.Option option, String contractAddr, BigInteger upperBound) throws Exception {
        return account.call(option, contract, "setSponsorForGas", new Address(contractAddr), new Uint256(upperBound));
    }

    public String addPrivilegeByAdmin(Account.Option option, String contractAddr, String[] address) throws Exception {
        List<Address> list = Arrays.stream(address).map(a -> new Address(a)).collect(Collectors.toList());
        return account.call(option, contract, "addPrivilegeByAdmin", new Address(contractAddr), new DynamicArray<Address>(Address.class, list));
    }

    public String removePrivilegeByAdmin(Account.Option option, String contractAddr, String[] address) throws Exception {
        List<Address> list = Arrays.stream(address).map(a -> new Address(a)).collect(Collectors.toList());
        return account.call(option, contract, "removePrivilegeByAdmin", new Address(contractAddr), new DynamicArray<Address>(Address.class, list));
    }
}

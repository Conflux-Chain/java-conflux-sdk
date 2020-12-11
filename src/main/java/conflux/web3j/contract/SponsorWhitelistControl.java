package conflux.web3j.contract;

import conflux.web3j.Account;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import conflux.web3j.RpcException;
import conflux.web3j.contract.abi.DecodeUtil;
import org.apache.commons.math3.analysis.function.Add;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.generated.Uint256;

public class SponsorWhitelistControl extends ContractCall {
    static String contract = "0x0888000000000000000000000000000000000001";
    private Account account;

    public SponsorWhitelistControl(Account account) {
        super(account.getCfx(), this.contract);
        this.account = account;
    }

    public String getSponsorForGas(String address) throws RpcException {
        String encodedResult = this.call("getSponsorForGas", new Address(address)).sendAndGet();
        return DecodeUtil.decode(encodedResult, Address.class);
    }

    public BigInteger getSponsoredBalanceForGas(String address) throws RpcException {
        String encodedResult = this.call("getSponsoredBalanceForGas", new Address(address)).sendAndGet();
        return DecodeUtil.decode(encodedResult, Uint256.class);
    }

    public BigInteger getSponsoredGasFeeUpperBound(String address) throws RpcException {
        String encodedResult = this.call("getSponsoredGasFeeUpperBound", new Address(address)).sendAndGet();
        return DecodeUtil.decode(encodedResult, Uint256.class);
    }

    public String getSponsorForCollateral(String address) throws RpcException {
        String encodedResult = this.call("getSponsorForCollateral", new Address(address)).sendAndGet();
        return DecodeUtil.decode(encodedResult, Address.class);
    }

    public BigInteger getSponsoredBalanceForCollateral(String address) throws RpcException {
        String encodedResult = this.call("getSponsoredBalanceForCollateral", new Address(address)).sendAndGet();
        return DecodeUtil.decode(encodedResult, Uint256.class);
    }

    public boolean isWhitelisted(String address, String user) throws RpcException {
        String encodedResult = this.call("isWhitelisted", new Address(address), new Address(user)).sendAndGet();
        return DecodeUtil.decode(encodedResult, Bool.class);
    }

    public boolean isAllWhitelisted(String address) throws RpcException {
        String encodedResult = this.call("isAllWhitelisted", new Address(address)).sendAndGet();
        return DecodeUtil.decode(encodedResult, Bool.class);
    }

    public void addPrivilege(Account.Option option, String[] addresses) throws Exception {
        List<Address> list = Arrays.stream(addresses).map(a -> new Address(a)).collect(Collectors.toList());
        account.call(option, contract, "addPrivilege", new DynamicArray<Address>(Address.class, list));
    }

    public void removePrivilege(Account.Option option, String[] addresses) throws Exception {
        List<Address> list = Arrays.stream(addresses).map(a -> new Address(a)).collect(Collectors.toList());
        account.call(option, contract, "removePrivilege", new DynamicArray<Address>(Address.class, list));
    }

    public void setSponsorForCollateral(Account.Option option, String address) throws Exception {
        account.call(option, contract, "setSponsorForCollateral", new Address(address));
    }

    public void setSponsorForGas(Account.Option option, String address, BigInteger upperBound) throws Exception {
        account.call(option, contract, "setSponsorForGas", new Address(address), new Uint256(upperBound));
    }

    public void addPrivilegeByAdmin(Account.Option option, String address, String[] addresses) throws Exception {
        List<Address> list = Arrays.stream(addresses).map(a -> new Address(a)).collect(Collectors.toList());
        account.call(option, contract, "addPrivilegeByAdmin", new Address(address), new DynamicArray<Address>(Address.class, list));
    }

    public void removePrivilegeByAdmin(Account.Option option, String address, String[] addresses) throws Exception {
        List<Address> list = Arrays.stream(addresses).map(a -> new Address(a)).collect(Collectors.toList());
        account.call(option, contract, "removePrivilegeByAdmin", new Address(address), new DynamicArray<Address>(Address.class, list));
    }
}

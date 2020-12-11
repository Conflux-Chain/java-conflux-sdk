package conflux.web3j.contract;

import conflux.web3j.Account;

import java.math.BigInteger;

import conflux.web3j.RpcException;
import conflux.web3j.contract.abi.DecodeUtil;
import org.web3j.abi.datatypes.Address;
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

    public String getSponsoredBalanceForGas(String address) throws RpcException {
        String encodedResult = this.call("getSponsoredBalanceForGas", new Address(address)).sendAndGet();
        return DecodeUtil.decode(encodedResult, Address.class);
    }

    public String getSponsoredGasFeeUpperBound(String address) throws RpcException {
        String encodedResult = this.call("getSponsoredGasFeeUpperBound", new Address(address)).sendAndGet();
        return DecodeUtil.decode(encodedResult, Address.class);
    }

    public String getSponsorForCollateral(String address) throws RpcException {
        String encodedResult = this.call("getSponsorForCollateral", new Address(address)).sendAndGet();
        return DecodeUtil.decode(encodedResult, Address.class);
    }

    public String getSponsoredBalanceForCollateral(String address) throws RpcException {
        String encodedResult = this.call("getSponsoredBalanceForCollateral", new Address(address)).sendAndGet();
        return DecodeUtil.decode(encodedResult, Address.class);
    }

    public String isWhitelisted(String address) throws RpcException {
        String encodedResult = this.call("isWhitelisted", new Address(address)).sendAndGet();
        return DecodeUtil.decode(encodedResult, Address.class);
    }

    public String isAllWhitelisted(String address) throws RpcException {
        String encodedResult = this.call("isAllWhitelisted", new Address(address)).sendAndGet();
        return DecodeUtil.decode(encodedResult, Address.class);
    }

    public void addPrivilege(Account.Option option, Address[] addresses) throws Exception {
        account.call(option, contract, "addPrivilege", addresses);
    }

    public void removePrivilege(Account.Option option, Address[] addresses) throws Exception {
        account.call(option, contract, "removePrivilege", addresses);
    }

    public void setSponsorForCollateral(Account.Option option, String address) throws Exception {
        account.call(option, contract, "setSponsorForCollateral", new Address(address));
    }

    public void setSponsorForGas(Account.Option option, String address, BigInteger upperBound) throws Exception {
        account.call(option, contract, "setSponsorForGas", new Address(address), new Uint256(upperBound));
    }

    public void addPrivilegeByAdmin(Account.Option option, String address, BigInteger upperBound) throws Exception {
        account.call(option, contract, "addPrivilegeByAdmin", new Address(address), new Uint256(upperBound));
    }

    public void removePrivilegeByAdmin(Account.Option option, String address, BigInteger upperBound) throws Exception {
        account.call(option, contract, "removePrivilegeByAdmin", new Address(address), new Uint256(upperBound));
    }
}

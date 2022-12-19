package conflux.web3j.contract.cns;

import conflux.web3j.Account;
import conflux.web3j.Account.Option;
import conflux.web3j.Cfx;
import conflux.web3j.RpcException;
import conflux.web3j.contract.ContractCall;
import conflux.web3j.types.CfxAddress;
import org.web3j.abi.DefaultFunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint32;
import org.web3j.abi.datatypes.generated.Uint64;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

public class Web3Controller extends ContractCall {
    private final static String contract = "0x82d5c987c72dfa84b8bad96ddae3f7661f8d7180";
    private Account account;
    private CfxAddress contractAddress;

    public static class Price extends StaticStruct {
        public BigInteger base;
        public BigInteger premium;

        public Price(Uint256 base, Uint256 premium) {
            super(base,premium);
            this.base = base.getValue();
            this.premium = premium.getValue();
        }
    }

    public Web3Controller(Account account) {
        super(account.getCfx(), new CfxAddress(Web3Controller.contract, account.getCfx().getIntNetworkId()));
        this.contractAddress = new CfxAddress(Web3Controller.contract, account.getCfx().getIntNetworkId());
        this.account = account;
    }

    public Web3Controller(Cfx cfx) {
        super(cfx, new CfxAddress(Web3Controller.contract, cfx.getIntNetworkId()));
        this.contractAddress = new CfxAddress(Web3Controller.contract, cfx.getIntNetworkId());
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public byte[] makeCommitment(Utf8String cnsName, Address owner, Uint256 duration, Bytes32 secret, Address resolver, DynamicArray<DynamicBytes> data, Bool reverseRecord, Uint32 fuses, Uint64 wrapperExpiry) throws RpcException {
        return this.callAndGet(Bytes32.class,"makeCommitment", cnsName, owner, duration, secret, resolver, data, reverseRecord, fuses, wrapperExpiry);
    }

    public Price rentPrice(Utf8String cnsName, Uint256 duration) throws RpcException {
        String hexString = this.call("rentPrice", cnsName, duration).sendAndGet();
        TypeReference ts = new TypeReference<Price>() {};
        List<Type> list = DefaultFunctionReturnDecoder.decode(hexString, Arrays.asList(ts));
        return (Price)list;
    }

    public Price rentPriceInFiat(Utf8String cnsName, Uint256 duration) throws RpcException {
        String hexString = this.call("rentPriceInFiat", cnsName, duration).sendAndGet();
        TypeReference ts = new TypeReference<Price>() {};
        List<Price> list = DefaultFunctionReturnDecoder.decode(hexString, Arrays.asList(ts));
        return (Price)list;
    }

    public Boolean valid(Utf8String cnsName) throws RpcException {
        return this.callAndGet(Bool.class,"valid", cnsName);
    }

    public Boolean available(Utf8String cnsName) throws RpcException {
        return this.callAndGet(Bool.class,"available", cnsName);
    }

    public Boolean labelStatus(Utf8String label) throws RpcException {
        return this.callAndGet(Bool.class,"labelStatus", label);
    }

    public String commit(Account.Option option, Bytes32 commitment) throws Exception {
        return account.call(option, this.contractAddress, "commit", commitment);
    }

    public String register(Account.Option option, Utf8String cnsName, Address owner, Uint256 duration, Bytes32 secret, Address resolver, DynamicArray<DynamicBytes> data, Bool reverseRecord, Uint32 fuses, Uint64 wrapperExpiry) throws Exception {
        return account.call(option, this.contractAddress, "register", cnsName, owner, duration, secret, resolver, data, reverseRecord, fuses, wrapperExpiry);
    }

    public String registerWithFiat(Account.Option option, Utf8String cnsName, Address owner, Uint256 duration, Bytes32 secret, Address resolver, DynamicArray<DynamicBytes> data, Bool reverseRecord, Uint32 fuses, Uint64 wrapperExpiry) throws Exception {
        return account.call(option, this.contractAddress, "registerWithFiat", cnsName, owner, duration, secret, resolver, data, reverseRecord, fuses, wrapperExpiry);
    }

    public String renew(Account.Option option, Utf8String cnsName, Uint256 duration) throws Exception {
        return account.call(option, this.contractAddress, "renew", cnsName, duration);
    }

    public String withdraw(Account.Option option) throws Exception {
        return account.call(option, this.contractAddress, "withdraw");
    }

}

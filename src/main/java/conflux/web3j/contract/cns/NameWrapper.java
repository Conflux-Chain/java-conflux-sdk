package conflux.web3j.contract.cns;

import conflux.web3j.Account;
import conflux.web3j.Account.Option;
import conflux.web3j.Cfx;
import conflux.web3j.RpcException;
import conflux.web3j.contract.ContractCall;
import conflux.web3j.contract.abi.TupleDecoder;
import conflux.web3j.types.CfxAddress;
import org.web3j.abi.DefaultFunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint32;
import org.web3j.abi.datatypes.generated.Uint64;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

public class NameWrapper extends ContractCall {
    private final static String contract = "0x862d4eb4b30d71d5a1020ca8f972c8914470955e";
    private Account account;
    private CfxAddress contractAddress;

    public NameWrapper(Account account) {
        super(account.getCfx(), new CfxAddress(NameWrapper.contract, account.getCfx().getIntNetworkId()));
        this.contractAddress = new CfxAddress(NameWrapper.contract, account.getCfx().getIntNetworkId());
        this.account = account;
    }

    public NameWrapper(Cfx cfx) {
        super(cfx, new CfxAddress(NameWrapper.contract, cfx.getIntNetworkId()));
        this.contractAddress = new CfxAddress(NameWrapper.contract, cfx.getIntNetworkId());
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String renew(Option option, Uint256 cnsName, Uint256 duration, Uint32 fuses, Uint64 expiry) throws Exception {
        return account.call(option, this.contractAddress, "renew", cnsName, duration, fuses, expiry);
    }

    public String wrap(Option option, Bytes name, Address wrappedOwner, Address resolver) throws Exception {
        return account.call(option, this.contractAddress, "wrap", name, wrappedOwner, resolver);
    }

    public String unwrap(Option option, Bytes32 parentNode, Bytes32 labelHash, Address controller) throws Exception {
        return account.call(option, this.contractAddress, "unwrap", parentNode, labelHash, controller);
    }

    public String setSubnodeOwner(Option option, Bytes32 parentNode, Utf8String label, Address owner, Uint32 fuses, Uint64 expiry) throws Exception {
        return account.call(option, this.contractAddress, "setSubnodeOwner", parentNode, label, owner, fuses, expiry);
    }

    public String setSubnodeRecord(Option option, Bytes32 parentNode, Utf8String label, Address owner, Address resolver, Uint64 ttl, Uint32 fuses, Uint64 expiry) throws Exception {
        return account.call(option, this.contractAddress, "setSubnodeRecord", parentNode, label, owner, resolver, ttl, fuses, expiry);
    }

    public String setRecord(Option option, Bytes32 node, Address owner, Address resolver, Uint64 ttl) throws Exception {
        return account.call(option, this.contractAddress, "setRecord", node, owner, resolver, ttl);
    }

    public String setResolver(Option option, Bytes32 node, Address resolver) throws Exception {
        return account.call(option, this.contractAddress, "setResolver", node, resolver);
    }

    public String setFuses(Option option, Bytes32 node, Uint32 fuses) throws Exception {
        return account.call(option, this.contractAddress, "setFuses", node, fuses);
    }

    public String setTTL(Option option, Bytes32 node, Uint64 ttl) throws Exception {
        return account.call(option, this.contractAddress, "setTTL", node, ttl);
    }

    public String setChildFuses(Option option, Bytes32 node, Uint32 fuses, Uint64 expiry) throws Exception {
        return account.call(option, this.contractAddress, "setChildFuses", node, fuses, expiry);
    }

    public String wrapETH2LD(Option option, Utf8String label, Address wrappedOwner, Uint32 fuses, Uint64 expiry, Address resolver) throws Exception {
        return account.call(option, this.contractAddress, "wrapETH2LD", label, wrappedOwner, fuses, expiry, resolver);
    }

    public String unwrapETH2LD(Option option, Bytes32 labelHash, Address registrant, Address controller) throws Exception {
        return account.call(option, this.contractAddress, "unwrapETH2LD", labelHash, registrant, controller);
    }

    public String upgradeETH2LD(Option option, Utf8String label, Address wrappedOwner, Address resolver) throws Exception {
        return account.call(option, this.contractAddress, "upgradeETH2LD", label, wrappedOwner, resolver);
    }

    public String upgrade(Option option, Bytes32 parentNode, Utf8String label, Address wrappedOwner, Address resolver) throws Exception {
        return account.call(option, this.contractAddress, "upgrade", parentNode, label, wrappedOwner, resolver);
    }

    public String registerAndWrapETH2LD(Option option, Utf8String label, Address wrappedOwner, Uint256 duration, Address resolver, Uint32 fuses, Uint64 expiry) throws Exception {
        return account.call(option, this.contractAddress, "registerAndWrapETH2LD", label, wrappedOwner, duration, resolver, fuses, expiry);
    }

    public String onERC721Received(Option option, Address to, Uint256 tokenId, Bytes data) throws Exception {
        return account.call(option, this.contractAddress, "onERC721Received", tokenId, data);
    }

    public String safeTransferFrom(Option option, Address from, Address to, Uint256 id, Uint256 amount, Bytes data) throws Exception {
        return account.call(option, this.contractAddress, "safeTransferFrom", from, to, id, amount, data);
    }

    public String safeBatchTransferFrom(Option option, Address from, Address to, DynamicArray<Uint256> ids, DynamicArray<Uint256> amounts, Bytes data) throws Exception {
        return account.call(option, this.contractAddress, "safeTransferFrom", from, to, ids, amounts, data);
    }

    public Address ownerOf(Uint256 id) throws RpcException {
        return new Address (this.callAndGet(Address.class,"ownerOf", id));
    }

    public String uri(Uint256 tokenId) throws RpcException {
        return this.callAndGet(Utf8String.class,"uri", tokenId);
    }

    public Boolean isTokenOwnerOrApproved(Bytes32 node, Address address) throws RpcException {
        return this.callAndGet(Bool.class,"isTokenOwnerOrApproved", node, address);
    }

    public Boolean allFusesBurned(Bytes32 node, Uint32 fuseMask) throws RpcException {
        return this.callAndGet(Bool.class,"allFusesBurned", node, fuseMask);
    }

    public Boolean isWrapped(Bytes32 node) throws RpcException {
        return this.callAndGet(Bool.class,"isWrapped", node);
    }

    public List<Bytes32> userNodeSet(Address user) throws RpcException {
        TypeReference<DynamicArray<Bytes32>> ts = new TypeReference<DynamicArray<Bytes32>>() {};
        return this.callAndGet(ts,"userNodeSet", user);
    }

    public List<Utf8String> userDomains(Address user) throws RpcException {
        TypeReference<DynamicArray<Utf8String>> ts = new TypeReference<DynamicArray<Utf8String>>() {};
        return this.callAndGet(ts,"userDomains", user);
    }

    public DataInfo getData(Uint256 id) throws RpcException {
        String rawData = this.callAndGet(Address.class,"getData", id);
        rawData = Numeric.cleanHexPrefix(rawData);
        TupleDecoder decoder = new TupleDecoder(rawData);
        String address = decoder.nextAddress();
        BigInteger fuses = decoder.nextUint256();
        BigInteger expiry = decoder.nextUint256();

        return new DataInfo(fuses, expiry, address);
    }
    public class DataInfo<String, BigInteger> {
        public final String address;
        public final BigInteger fuses;
        public final BigInteger expiry;
        public DataInfo(BigInteger a, BigInteger b, String c) {
            fuses = a;
            expiry = b;
            address = c;
        }
    }

}

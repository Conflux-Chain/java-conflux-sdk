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
import org.web3j.abi.datatypes.generated.*;
import org.web3j.crypto.Hash;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

public class PublicResolver extends ContractCall {
    private final static String contract = "0x8829926ab30d8e795ad216296c2a01a525417bca";
    private Account account;
    private CfxAddress contractAddress;

    public PublicResolver(Account account) {
        super(account.getCfx(), new CfxAddress(PublicResolver.contract, account.getCfx().getIntNetworkId()));
        this.contractAddress = new CfxAddress(PublicResolver.contract, account.getCfx().getIntNetworkId());
        this.account = account;
    }

    public PublicResolver(Cfx cfx) {
        super(cfx, new CfxAddress(PublicResolver.contract, cfx.getIntNetworkId()));
        this.contractAddress = new CfxAddress(PublicResolver.contract, cfx.getIntNetworkId());
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String setAddr(Option option, Bytes32 node, Address addr) throws Exception {
        return account.call(option, this.contractAddress, "setAddr", node, addr);
    }

    public String setAddr(Option option, Bytes32 node, Address addr, Bytes a) throws Exception {
        return account.call(option, this.contractAddress, "setAddr", node, addr, a);
    }

    public String setName(Option option, Bytes32 node, Utf8String name) throws Exception {
        return account.call(option, this.contractAddress, "setName", node, name);
    }

    public String setContenthash(Option option, Bytes32 node, Bytes hash) throws Exception {
        return account.call(option, this.contractAddress, "setContenthash", node, hash);
    }

    public String setABI(Option option, Bytes32 node, Uint256 contentType, DynamicBytes data) throws Exception {
        return account.call(option, this.contractAddress, "setABI", node, contentType, data);
    }

    public String setPubkey(Option option, Bytes32 node, Bytes32 x, Bytes32 y) throws Exception {
        return account.call(option, this.contractAddress, "setPubkey", node, x, y);
    }

    public String setText(Option option, Bytes32 node, Utf8String key, Utf8String value) throws Exception {
        return account.call(option, this.contractAddress, "setText", node, key, value);
    }

    public String multicall(Option option, DynamicArray<Bytes> data) throws Exception {
        return account.call(option, this.contractAddress, "multicall", data);
    }

    public byte[] addr(Bytes32 node, Uint coinType) throws RpcException {
        return this.callAndGet(Bytes.class,"addr", node, coinType);
    }

    public conflux.web3j.types.Address addr(Bytes32 node) throws RpcException {
        String rawData = this.callAndGet(Address.class,"addr", node);

        return new conflux.web3j.types.Address(Numeric.hexStringToByteArray(rawData), account.getCfx().getIntNetworkId());

    }

    public String name(Bytes32 node) throws RpcException {
        return this.callAndGet(Utf8String.class,"name", node);
    }

    public byte[] getContentHash(Bytes32 node) throws RpcException {
        return this.callAndGet(Bytes32.class,"contenthash", node);
    }

    public ABIInfo getABI(Bytes32 node, Uint256 contentTypes) throws RpcException {
        BigInteger[] res = new BigInteger[2];

        String rawData = this.call("ABI", node, contentTypes).sendAndGet();
        rawData = Numeric.cleanHexPrefix(rawData);
        TupleDecoder decoder = new TupleDecoder(rawData);
        BigInteger type = decoder.nextUint256();
        return new ABIInfo(new Uint256(type), new Bytes32(Numeric.hexStringToByteArray(decoder.next())));
    }

    public String text(Bytes32 node, Utf8String key) throws RpcException {
        return this.callAndGet(Utf8String.class,"text", node, key);
    }

    public Boolean supportsInterface(Bytes4 interfaceID) throws RpcException {
        return this.callAndGet(Bool.class,"supportsInterface", interfaceID);
    }

    public byte[][] pubkey(Bytes32 node) throws RpcException {
        byte[][] res = new byte[2][];
        String rawData = this.call("pubkey", node).sendAndGet();
        rawData = Numeric.cleanHexPrefix(rawData);
        TupleDecoder decoder = new TupleDecoder(rawData);

        res[0] = decoder.nextUint256().toByteArray();
        res[1] = decoder.nextUint256().toByteArray();
        return res;
    }

    public static class ABIInfo extends DynamicStruct {
        public BigInteger typeId;
        public byte[] data;

        public ABIInfo(Uint256 typeId, Bytes32 data) {
            super(typeId, data);
            this.typeId = typeId.getValue();
            this.data = data.getValue();
        }
    }
}


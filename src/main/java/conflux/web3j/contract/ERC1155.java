package conflux.web3j.contract;

import conflux.web3j.Account;
import conflux.web3j.Cfx;
import conflux.web3j.RpcException;
import conflux.web3j.contract.abi.DecodeUtil;
import conflux.web3j.types.CfxAddress;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.Utils;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

public class ERC1155 extends ContractCall {
    private Account account;
    private CfxAddress contract;

    private static final TypeReference<DynamicArray<Uint256>> TYPE_DYNAMIC_ARRAY_UINT256 = new TypeReference<DynamicArray<Uint256>>() {};

    public ERC1155(Cfx cfx, CfxAddress address) {
        super(cfx, address);
    }

    public ERC1155(Cfx cfx, CfxAddress address, Account account) {
        super(cfx, address);
        this.account = account;
        this.contract = address;
    }

    public BigInteger balanceOf(Address account, BigInteger id) throws RpcException {
        return this.callAndGet(Uint256.class, "balanceOf", account, new Uint256(id));
    }

    public BigInteger balanceOf(String hexAccount, BigInteger id) throws RpcException {
        return this.balanceOf(new Address(hexAccount), id);
    }

    public BigInteger balanceOf(CfxAddress account, BigInteger id) throws RpcException {
        return this.balanceOf(account.getABIAddress(), id);
    }

    public String uri(BigInteger id) throws RpcException {
        return this.callAndGet(Utf8String.class, "uri", new Uint256(id));
    }

    public List<BigInteger> balanceOfBatch(List<String> accounts, List<BigInteger> ids) throws RpcException {
        String rawData = this.call(
                "balanceOfBatch",
                new DynamicArray<Address>(Address.class, Utils.typeMap(accounts, Address.class)),
                new DynamicArray<Uint256>(Uint256.class, Utils.typeMap(ids, Uint256.class))
            ).sendAndGet();
        List<Uint256> balances = DecodeUtil.decode(rawData, TYPE_DYNAMIC_ARRAY_UINT256);
        assert balances != null;
        return balances.stream().map(NumericType::getValue).collect(Collectors.toList());
    }

    public Boolean isApprovedForAll(Address owner, Address operator) throws RpcException {
        return this.callAndGet(Bool.class, "isApprovedForAll", owner, operator);
    }

    public Boolean isApprovedForAll(CfxAddress owner, CfxAddress operator) throws RpcException {
        return this.isApprovedForAll(owner.getABIAddress(), operator.getABIAddress());
    }

    public Boolean isApprovedForAll(String hexOwner, String operator) throws RpcException {
        return this.isApprovedForAll(new Address(hexOwner), new Address(operator));
    }

    public String setApprovalForAll(Account.Option option, Address operator, boolean approved) throws Exception {
        return this.account.call(option, this.contract, "setApprovalForAll", operator, new Bool(approved));
    }

    public String setApprovalForAll(Account.Option option, String hexOperator, boolean approved) throws Exception {
        return this.setApprovalForAll(option, new Address(hexOperator), approved);
    }

    public String setApprovalForAll(Account.Option option, CfxAddress operator, boolean approved) throws Exception {
        return this.setApprovalForAll(option, operator.getABIAddress(), approved);
    }

    public String safeTransferFrom(Account.Option option, Address from, Address to, BigInteger tokenId, BigInteger amount, byte[] data) throws Exception {
        return this.account.call(option, this.contract, "safeTransferFrom", from, to, new Uint256(tokenId), new Uint256(amount), new DynamicBytes(data));
    }

    public String safeTransferFrom(Account.Option option, CfxAddress from, CfxAddress to, BigInteger tokenId, BigInteger amount, byte[] data) throws Exception {
        return this.safeTransferFrom(option, from.getABIAddress(), to.getABIAddress(), tokenId, amount, data);
    }

    public String safeTransferFrom(Account.Option option, String hexFrom, String hexTo, BigInteger tokenId, BigInteger amount, byte[] data) throws Exception {
        return this.safeTransferFrom(option, new Address(hexFrom), new Address(hexTo), tokenId, amount, data);
    }

    public String safeBatchTransferFrom(Account.Option option, Address from, Address to, List<BigInteger> tokenIds, List<BigInteger> amounts, byte[] data) throws Exception {
        return this.account.call(
                option,
                this.contract,
                "safeTransferFrom",
                from,
                to,
                new DynamicArray<Uint256>(Uint256.class, Utils.typeMap(tokenIds, Uint256.class)),
                new DynamicArray<Uint256>(Uint256.class, Utils.typeMap(amounts, Uint256.class)),
                new DynamicBytes(data)
        );
    }

    public String safeBatchTransferFrom(Account.Option option, CfxAddress from, CfxAddress to, List<BigInteger> tokenIds, List<BigInteger> amounts, byte[] data) throws Exception {
        return this.safeBatchTransferFrom(option, from.getABIAddress(), to.getABIAddress(), tokenIds, amounts, data);
    }

    public String safeBatchTransferFrom(Account.Option option, String hexFrom, String hexTo, List<BigInteger> tokenIds, List<BigInteger> amounts, byte[] data) throws Exception {
        return this.safeBatchTransferFrom(option, new Address(hexFrom), new Address(hexTo), tokenIds, amounts, data);
    }

    public String _mint(Account.Option option, Address to, BigInteger id, BigInteger amount, byte[] data) throws Exception {
        return this.account.call(option, this.contract, "_mint", to, new Uint256(id), new Uint256(amount), new DynamicBytes(data));
    }

    public String _mint(Account.Option option, String hexTo, BigInteger id, BigInteger amount, byte[] data) throws Exception {
        return this._mint(option, new Address(hexTo), id, amount, data);
    }

    public String _mint(Account.Option option, CfxAddress to, BigInteger id, BigInteger amount, byte[] data) throws Exception {
        return this._mint(option, to.getABIAddress(), id, amount, data);
    }

    public String _mintBatch(Account.Option option, Address to, List<BigInteger> ids, List<BigInteger> amounts, byte[] data) throws Exception {
        return this.account.call(
                option,
                this.contract,
                "_mintBatch",
                to,
                new DynamicArray<Uint256>(Uint256.class, Utils.typeMap(ids, Uint256.class)),
                new DynamicArray<Uint256>(Uint256.class, Utils.typeMap(amounts, Uint256.class)),
                new DynamicBytes(data)
        );
    }

    public String _mintBatch(Account.Option option, String hexTo, List<BigInteger> ids, List<BigInteger> amounts, byte[] data) throws Exception {
        return this._mintBatch(option, new Address(hexTo), ids, amounts, data);
    }

    public String _mintBatch(Account.Option option, CfxAddress to, List<BigInteger> ids, List<BigInteger> amounts, byte[] data) throws Exception {
        return this._mintBatch(option, to.getABIAddress(), ids, amounts, data);
    }
}

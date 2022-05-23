package conflux.web3j.contract;

import conflux.web3j.Account;
import conflux.web3j.Cfx;
import conflux.web3j.RpcException;
import conflux.web3j.types.CfxAddress;

import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;

import java.math.BigInteger;

public class ERC721 extends ContractCall {
    private Account account;
    private CfxAddress contract;

    public ERC721(Cfx cfx, CfxAddress address) {
        super(cfx, address);
    }

    public ERC721(Cfx cfx, CfxAddress address, Account account) {
        super(cfx, address);
        this.account = account;
        this.contract = address;
    }

    public String name() throws RpcException {
        return this.callAndGet(Utf8String.class, "name");
    }

    public String symbol() throws RpcException {
        return this.callAndGet(Utf8String.class, "symbol");
    }

    public String tokenURI(BigInteger tokenId) throws RpcException {
        return this.callAndGet(Utf8String.class, "tokenURI", new Uint256(tokenId));
    }

    public BigInteger balanceOf(Address account) throws RpcException {
        return this.callAndGet(Uint256.class, "balanceOf", account);
    }

    public BigInteger balanceOf(CfxAddress account) throws  RpcException {
        return this.balanceOf(account.getABIAddress());
    }

    // pass a hex40 address
    public BigInteger balanceOf(String account) throws  RpcException {
        return this.balanceOf(new Address(account));
    }

    public String ownerOf(BigInteger tokenId) throws RpcException {
        return this.callAndGet(Address.class, "ownerOf", new Uint256(tokenId));
    }

    public String getApproved(BigInteger tokenId) throws RpcException {
        return this.callAndGet(Address.class, "getApproved", new Uint256(tokenId));
    }

    public Boolean isApprovedForAll(Address owner, Address operator) throws RpcException {
        return this.callAndGet(Bool.class, "isApprovedForAll", owner, operator);
    }

    public Boolean isApprovedForAll(CfxAddress owner, CfxAddress operator) throws RpcException {
        return this.isApprovedForAll(owner.getABIAddress(), operator.getABIAddress());
    }

    public Boolean isApprovedForAll(String owner, String operator) throws RpcException {
        return this.isApprovedForAll(new Address(owner), new Address(operator));
    }

    public String _mint(Account.Option option, Address to, BigInteger tokenId) throws Exception {
        return this.account.call(option, this.contract, "_mint", to, new Uint256(tokenId));
    }

    public String _mint(Account.Option option, CfxAddress to, BigInteger tokenId) throws Exception {
        return this._mint(option, to.getABIAddress(), tokenId);
    }

    public String _mint(Account.Option option, String to, BigInteger tokenId) throws Exception {
        return this._mint(option, new Address(to), tokenId);
    }

    public String transferFrom(Account.Option option, Address sender, Address recipient, BigInteger tokenId) throws Exception {
        return this.account.call(option, this.contract, "transferFrom", sender, recipient, new Uint256(tokenId));
    }

    public String transferFrom(Account.Option option, CfxAddress sender, CfxAddress recipient, BigInteger tokenId) throws Exception {
        return this.transferFrom(option, sender.getABIAddress(), recipient.getABIAddress(), tokenId);
    }

    public String transferFrom(Account.Option option, String sender, String recipient, BigInteger tokenId) throws Exception {
        return this.transferFrom(option, new Address(sender), new Address(recipient), tokenId);
    }

    public String safeTransferFrom(Account.Option option, Address sender, Address recipient, BigInteger tokenId) throws Exception {
        return this.account.call(option, this.contract, "safeTransferFrom", sender, recipient, new Uint256(tokenId));
    }

    public String safeTransferFrom(Account.Option option, CfxAddress sender, CfxAddress recipient, BigInteger tokenId) throws Exception {
        return this.safeTransferFrom(option, sender.getABIAddress(), recipient.getABIAddress(), tokenId);
    }

    public String safeTransferFrom(Account.Option option, String sender, String recipient, BigInteger tokenId) throws Exception {
        return this.safeTransferFrom(option, new Address(sender), new Address(recipient), tokenId);
    }

    public String safeTransferFrom(Account.Option option, Address from, Address to, BigInteger tokenId, byte[] data) throws Exception {
        return this.account.call(option, this.contract, "safeTransferFrom", from, to, new Uint256(tokenId), new DynamicBytes(data));
    }

    public String safeTransferFrom(Account.Option option, CfxAddress from, CfxAddress to, BigInteger tokenId, byte[] data) throws Exception {
        return this.safeTransferFrom(option, from.getABIAddress(), to.getABIAddress(), tokenId, data);
    }

    public String safeTransferFrom(Account.Option option, String from, String to, BigInteger tokenId, byte[] data) throws Exception {
        return this.safeTransferFrom(option, new Address(from), new Address(to), tokenId, data);
    }

    public String approve(Account.Option option, Address to, BigInteger tokenId) throws Exception {
        return this.account.call(option, this.contract, "approve", to, new Uint256(tokenId));
    }

    public String approve(Account.Option option, CfxAddress to, BigInteger tokenId) throws Exception {
        return this.approve(option, to.getABIAddress(), tokenId);
    }

    public String approve(Account.Option option, String to, BigInteger tokenId) throws Exception {
        return this.approve(option, new Address(to), tokenId);
    }

    public String setApprovalForAll(Account.Option option, Address operator, boolean approved) throws Exception {
        return this.account.call(option, this.contract, "setApprovalForAll", operator, new Bool(approved));
    }

    public String setApprovalForAll(Account.Option option, CfxAddress operator, boolean approved) throws Exception {
        return this.setApprovalForAll(option, operator.getABIAddress(), approved);
    }

    public String setApprovalForAll(Account.Option option, String operator, boolean approved) throws Exception {
        return this.setApprovalForAll(option, new Address(operator), approved);
    }
}

package conflux.web3j.response;

import java.math.BigInteger;
import java.util.Optional;

import conflux.web3j.types.AccessListEntry;
import conflux.web3j.types.Address;
import org.web3j.utils.Numeric;
import java.util.List;

public class Transaction {
	
	public static class Response extends CfxNullableResponse<Transaction> {}
	
	private String hash;
	private String nonce;
	private String blockHash;
	private String transactionIndex;
	private Address from;
	private Address to;
	private String value;
	private String gasPrice;
	private String gas;
	private String contractCreated;
	private String data;
	private String storageLimit;
	private String epochHeight;
	private String chainId;
	private String status;
	private String v;
	private String r;
	private String s;
	private String type;
	private String yParity;
	private String maxFeePerGas;
	private String maxPriorityFeePerGas;
	private List<AccessListEntry> accessList;

	public BigInteger getMaxFeePerGas() {
		return Numeric.decodeQuantity(this.maxFeePerGas);
	}

	public void setMaxFeePerGas(String maxFeePerGas) {
		this.maxFeePerGas = maxFeePerGas;
	}

	public BigInteger getMaxPriorityFeePerGas() {
		return Numeric.decodeQuantity(this.maxPriorityFeePerGas);
	}

	public void setMaxPriorityFeePerGas(String maxPriorityFeePerGas) {
		this.maxPriorityFeePerGas = maxPriorityFeePerGas;
	}

	public BigInteger getYParity() {
		return Numeric.decodeQuantity(yParity);
	}

	public void setYParity(String yParity) {
		this.yParity = yParity;
	}

	public int getType() {
		return Numeric.decodeQuantity(type).intValue();
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<AccessListEntry> getAccessList() {
		return accessList;
	}

	public void setAccessList(List<AccessListEntry> accessList) {
		this.accessList = accessList;
	}
	
	public String getHash() {
		return hash;
	}
	
	public void setHash(String hash) {
		this.hash = hash;
	}
	
	public BigInteger getNonce() {
		return Numeric.decodeQuantity(this.nonce);
	}
	
	public void setNonce(String nonce) {
		this.nonce = nonce;
	}
	
	public Optional<String> getBlockHash() {
		if (this.blockHash == null || this.blockHash.isEmpty()) {
			return Optional.empty();
		} else {
			return Optional.of(this.blockHash);
		}
	}
	
	public void setBlockHash(String blockHash) {
		this.blockHash = blockHash;
	}
	
	public Optional<BigInteger> getTransactionIndex() {
		if (this.transactionIndex == null || this.transactionIndex.isEmpty()) {
			return Optional.empty();
		} else {
			return Optional.of(Numeric.decodeQuantity(this.transactionIndex));	
		}
	}
	
	public void setTransactionIndex(String transactionIndex) {
		this.transactionIndex = transactionIndex;
	}
	
	public Address getFrom() {
		return from;
	}
	
	public void setFrom(Address from) {
		this.from = from;
	}
	
	public Optional<Address> getTo() {
		if (this.to == null) {
			return Optional.empty();
		} else {
			return Optional.of(this.to);
		}
	}
	
	public void setTo(Address to) {
		this.to = to;
	}
	
	public BigInteger getValue() {
		return Numeric.decodeQuantity(this.value);
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public BigInteger getGasPrice() {
		return Numeric.decodeQuantity(this.gasPrice);
	}
	
	public void setGasPrice(String gasPrice) {
		this.gasPrice = gasPrice;
	}
	
	public BigInteger getGas() {
		return Numeric.decodeQuantity(this.gas);
	}
	
	public void setGas(String gas) {
		this.gas = gas;
	}
	
	public Optional<String> getContractCreated() {
		if (this.contractCreated == null || this.contractCreated.isEmpty()) {
			return Optional.empty();
		} else {
			return Optional.of(this.contractCreated);
		}
	}
	
	public void setContractCreated(String contractCreated) {
		this.contractCreated = contractCreated;
	}
	
	public String getData() {
		return data;
	}
	
	public void setData(String data) {
		this.data = data;
	}
	
	public BigInteger getStorageLimit() {
		return Numeric.decodeQuantity(this.storageLimit);
	}
	
	public void setStorageLimit(String storageLimit) {
		this.storageLimit = storageLimit;
	}
	
	public BigInteger getEpochHeight() {
		return Numeric.decodeQuantity(this.epochHeight);
	}
	
	public void setEpochHeight(String epochHeight) {
		this.epochHeight = epochHeight;
	}
	
	public BigInteger getChainId() {
		return Numeric.decodeQuantity(this.chainId);
	}
	
	public void setChainId(String chainId) {
		this.chainId = chainId;
	}
	
	public Optional<BigInteger> getStatus() {
		if (this.status == null || this.status.isEmpty()) {
			return Optional.empty();
		} else {
			return Optional.of(Numeric.decodeQuantity(this.status));	
		}
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public BigInteger getV() {
		return Numeric.decodeQuantity(this.v);
	}
	
	public void setV(String v) {
		this.v = v;
	}
	
	public BigInteger getR() {
		return Numeric.decodeQuantity(this.r);
	}
	
	public void setR(String r) {
		this.r = r;
	}
	
	public BigInteger getS() {
		return Numeric.decodeQuantity(this.s);
	}
	
	public void setS(String s) {
		this.s = s;
	}
}

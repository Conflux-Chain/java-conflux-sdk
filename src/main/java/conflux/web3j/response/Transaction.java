package conflux.web3j.response;

import java.math.BigInteger;
import java.util.Optional;

import org.web3j.utils.Numeric;

public class Transaction {
	private String hash;
	private String nonce;
	private String blockHash;
	private String transactionIndex;
	private String from;
	private String to;
	private String value;
	private String gasPrice;
	private String gas;
	private String contractCreated;
	private String data;
	private String status;
	private String v;
	private String r;
	private String s;
	
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
	
	public String getFrom() {
		return from;
	}
	
	public void setFrom(String from) {
		this.from = from;
	}
	
	public Optional<String> getTo() {
		if (this.to == null || this.to.isEmpty()) {
			return Optional.empty();
		} else {
			return Optional.of(this.to);
		}
	}
	
	public void setTo(String to) {
		this.to = to;
	}
	
	public String getValue() {
		return value;
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

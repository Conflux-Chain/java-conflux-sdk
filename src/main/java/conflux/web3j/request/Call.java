package conflux.web3j.request;

import java.math.BigInteger;
import conflux.web3j.types.Address;
import org.web3j.utils.Numeric;

public class Call {
	private Address from;
	private Address to;
	private BigInteger gasPrice;
	private BigInteger gas;
	private BigInteger value;
	private String data;
	private BigInteger nonce;
	private BigInteger storageLimit;
	
	public Address getFrom() {
		return from;
	}
	
	public void setFrom(Address from) {
		this.from = from;
	}

	public Address getTo() {
		return to;
	}
	
	public void setTo(Address to) {
		this.to = to;
	}

	public String getGasPrice() {
		return this.gasPrice == null ? null : Numeric.encodeQuantity(this.gasPrice);
	}
	
	public void setGasPrice(BigInteger gasPrice) {
		this.gasPrice = gasPrice;
	}
	
	public String getGas() {
		return this.gas == null ? null : Numeric.encodeQuantity(this.gas);
	}
	
	public void setGas(BigInteger gas) {
		this.gas = gas;
	}
	
	public String getValue() {
		return this.value == null ? null : Numeric.encodeQuantity(this.value);
	}
	
	public void setValue(BigInteger value) {
		this.value = value;
	}
	
	public String getData() {
		return this.data == null ? null : Numeric.prependHexPrefix(this.data);
	}
	
	public void setData(String data) {
		this.data = data;
	}
	
	public String getNonce() {
		return this.nonce == null ? null : Numeric.encodeQuantity(this.nonce);
	}
	
	public void setNonce(BigInteger nonce) {
		this.nonce = nonce;
	}
	
	public String getStorageLimit() {
		return this.storageLimit == null ? null : Numeric.encodeQuantity(this.storageLimit);
	}
	
	public void setStorageLimit(BigInteger storageLimit) {
		this.storageLimit = storageLimit;
	}
}

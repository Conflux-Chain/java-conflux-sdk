package conflux.web3j.types;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Sign;
import org.web3j.rlp.RlpEncoder;
import org.web3j.rlp.RlpList;
import org.web3j.rlp.RlpString;
import org.web3j.rlp.RlpType;
import org.web3j.utils.Bytes;
import org.web3j.utils.Numeric;

import conflux.web3j.CfxUnit;

public class RawTransaction {
	
	private static AtomicReference<BigInteger> DefaultGasPrice = new AtomicReference<BigInteger>(CfxUnit.DEFAULT_GAS_PRICE);
	private static AtomicReference<BigInteger> DefaultChainId = new AtomicReference<BigInteger>(BigInteger.ZERO);
	
	public static BigInteger getDefaultGasPrice() {
		return DefaultGasPrice.get();
	}
	
	public static void setDefaultGasPrice(BigInteger defaultGasPrice) {
		DefaultGasPrice.set(defaultGasPrice);
	}
	
	public static BigInteger getDefaultChainId() {
		return DefaultChainId.get();
	}
	
	public static void setDefaultChainId(BigInteger defaultChainId) {
		DefaultChainId.set(defaultChainId);
	}

	private BigInteger nonce;
	private BigInteger gasPrice;
	private BigInteger gas;
	private String to;
	private BigInteger value;
	private BigInteger storageLimit;
	private BigInteger epochHeight;
	private BigInteger chainId;
	private String data;
	
	public static RawTransaction create(BigInteger nonce, BigInteger gas, String to, BigInteger value, BigInteger storageLimit, BigInteger epochHeight, String data) {
		RawTransaction tx = new RawTransaction();
		
		tx.nonce = nonce;
		tx.gasPrice = DefaultGasPrice.get();
		tx.gas = gas;
		tx.to = to;
		tx.value = value;
		tx.storageLimit = storageLimit;
		tx.epochHeight = epochHeight;
		tx.chainId = DefaultChainId.get();
		tx.data = data;
		
		return tx;
	}
	
	public static RawTransaction transfer(BigInteger nonce, String to, BigInteger value, BigInteger epochHeight) {
		return create(nonce, CfxUnit.DEFAULT_GAS_LIMIT, to, value, BigInteger.ZERO, epochHeight, null);
	}
	
	public static RawTransaction deploy(BigInteger nonce, BigInteger gas, BigInteger storageLimit, BigInteger epochHeight, String bytecodes) {
		return create(nonce, gas, "", BigInteger.ZERO, storageLimit, epochHeight, bytecodes);
	}
	
	public static RawTransaction deploy(BigInteger nonce, BigInteger gas, BigInteger value, BigInteger storageLimit, BigInteger epochHeight, String bytecodes) {
		return create(nonce, gas, "", value, storageLimit, epochHeight, bytecodes);
	}
	
	public static RawTransaction call(BigInteger nonce, BigInteger gas, String to, BigInteger storageLimit, BigInteger epochHeight, String data) {
		return create(nonce, gas, to, BigInteger.ZERO, storageLimit, epochHeight, data);
	}
	
	public String sign(ECKeyPair ecKeyPair) {
		RlpType rlpTx = this.toRlp();
		
		byte[] encoded = RlpEncoder.encode(rlpTx);
		Sign.SignatureData signature = Sign.signMessage(encoded, ecKeyPair);
		
		int v = signature.getV()[0] - 27;
		byte[] r = Bytes.trimLeadingZeroes(signature.getR());
		byte[] s = Bytes.trimLeadingZeroes(signature.getS());
		
		byte[] signedTx = RlpEncoder.encode(new RlpList(
				rlpTx,
				RlpString.create(v),
				RlpString.create(r),
				RlpString.create(s)));
		
		return Numeric.toHexString(signedTx);
	}
	
	public RlpType toRlp() {
		List<RlpType> values = new ArrayList<RlpType>();

		values.add(RlpString.create(this.nonce));
		values.add(RlpString.create(this.gasPrice));
		values.add(RlpString.create(this.gas));

		if (this.to != null && !this.to.isEmpty()) {
			values.add(RlpString.create(Numeric.hexStringToByteArray(this.to)));
		} else {
			values.add(RlpString.create(""));
		}

		values.add(RlpString.create(this.value));
		values.add(RlpString.create(this.storageLimit));
		values.add(RlpString.create(this.epochHeight));
		values.add(RlpString.create(this.chainId));
		values.add(RlpString.create(Numeric.hexStringToByteArray(this.data == null ? "" : this.data)));

		return new RlpList(values);
	}
	
	public BigInteger getNonce() {
		return nonce;
	}
	
	public void setNonce(BigInteger nonce) {
		this.nonce = nonce;
	}
	
	public BigInteger getGasPrice() {
		return gasPrice;
	}
	
	public void setGasPrice(BigInteger gasPrice) {
		this.gasPrice = gasPrice;
	}
	
	public BigInteger getGas() {
		return gas;
	}
	
	public void setGas(BigInteger gas) {
		this.gas = gas;
	}
	
	public String getTo() {
		return to;
	}
	
	public void setTo(String to) {
		this.to = to;
	}
	
	public BigInteger getValue() {
		return value;
	}
	
	public void setValue(BigInteger value) {
		this.value = value;
	}
	
	public BigInteger getStorageLimit() {
		return storageLimit;
	}
	
	public void setStorageLimit(BigInteger storageLimit) {
		this.storageLimit = storageLimit;
	}
	
	public BigInteger getEpochHeight() {
		return epochHeight;
	}
	
	public void setEpochHeight(BigInteger epochHeight) {
		this.epochHeight = epochHeight;
	}
	
	public BigInteger getChainId() {
		return chainId;
	}
	
	public void setChainId(BigInteger chainId) {
		this.chainId = chainId;
	}
	
	public String getData() {
		return data;
	}
	
	public void setData(String data) {
		this.data = data;
	}

}

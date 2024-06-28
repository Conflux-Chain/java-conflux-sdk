package conflux.web3j.types;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.nio.ByteBuffer;
import java.util.Arrays;

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
	
	private static final AtomicReference<BigInteger> DefaultGasPrice = new AtomicReference<BigInteger>(BigInteger.ONE);
	private static final AtomicReference<BigInteger> DefaultChainId = new AtomicReference<BigInteger>(BigInteger.valueOf(1029));
	
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

	public static BigInteger TYPE_LEGACY = new BigInteger("0");
	public static BigInteger TYPE_2930 = new BigInteger("1");
	public static BigInteger TYPE_1559 = new BigInteger("2");

	private static final byte[] TYPE_2930_PREFIX = {99, 102, 120, 1}; // cfx + 1
	private static final byte[] TYPE_1559_PREFIX = {99, 102, 120, 2}; // cfx + 2

	private BigInteger type;
	private BigInteger nonce;
	private BigInteger gasPrice;
	private BigInteger maxPriorityFeePerGas;
	private BigInteger maxFeePerGas;
	private BigInteger gas;
	private Address to;
	private BigInteger value;
	private BigInteger storageLimit;
	private BigInteger epochHeight;
	private BigInteger chainId;
	private String data;
	private List<AccessListEntry> accessList;

	// Note default will use Mainnet chainId.
	// To create a testnet, user need invoke RawTransaction.setDefaultChainId(BigInteger.ONE) before invoke the create method.
	public static RawTransaction create(BigInteger nonce, BigInteger gas, Address to, BigInteger value, BigInteger storageLimit, BigInteger epochHeight, String data) {
		RawTransaction tx = new RawTransaction();
		
		tx.nonce = nonce;
//		tx.gasPrice = DefaultGasPrice.get();
		tx.gas = gas;
		tx.to = to;
		tx.value = value;
		tx.storageLimit = storageLimit;
		tx.epochHeight = epochHeight;
		tx.data = data;
		tx.chainId = DefaultChainId.get();
		tx.type = BigInteger.ZERO;
		
		return tx;
	}
	
	public static RawTransaction transfer(BigInteger nonce, Address to, BigInteger value, BigInteger epochHeight) {
		return create(nonce, CfxUnit.DEFAULT_GAS_LIMIT, to, value, BigInteger.ZERO, epochHeight, null);
	}
	
	public static RawTransaction deploy(BigInteger nonce, BigInteger gas, BigInteger storageLimit, BigInteger epochHeight, String bytecodes) {
		return create(nonce, gas, null, BigInteger.ZERO, storageLimit, epochHeight, bytecodes);
	}
	
	public static RawTransaction deploy(BigInteger nonce, BigInteger gas, BigInteger value, BigInteger storageLimit, BigInteger epochHeight, String bytecodes) {
		return create(nonce, gas, null, value, storageLimit, epochHeight, bytecodes);
	}
	
	public static RawTransaction call(BigInteger nonce, BigInteger gas, Address to, BigInteger storageLimit, BigInteger epochHeight, String data) {
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

		// add prefix for typed transaction
		if (Objects.equals(this.type, RawTransaction.TYPE_1559)) {
			signedTx = concat(TYPE_1559_PREFIX, signedTx);
		}
		if (Objects.equals(this.type, RawTransaction.TYPE_2930)) {
			signedTx = concat(TYPE_2930_PREFIX, signedTx);
		}
		
		return Numeric.toHexString(signedTx);
	}
	
	public RlpType toRlp() {
		List<RlpType> values = new ArrayList<RlpType>();

		values.add(RlpString.create(this.nonce));

		if (Objects.equals(this.type, RawTransaction.TYPE_1559)) {
			values.add(RlpString.create(this.maxPriorityFeePerGas));
			values.add(RlpString.create(this.maxFeePerGas));
		} else {
			values.add(RlpString.create(this.gasPrice));
		}

		values.add(RlpString.create(this.gas));

		if (this.to != null) {
			values.add(RlpString.create(Numeric.hexStringToByteArray(this.to.getHexAddress())));
		} else {
			values.add(RlpString.create(""));
		}

		values.add(RlpString.create(this.value));
		values.add(RlpString.create(this.storageLimit));
		values.add(RlpString.create(this.epochHeight));
		values.add(RlpString.create(this.chainId));
		values.add(RlpString.create(Numeric.hexStringToByteArray(this.data == null ? "" : this.data)));

		if (Objects.equals(this.type, RawTransaction.TYPE_1559) || Objects.equals(this.type, RawTransaction.TYPE_2930)) {
			List<RlpType> rlpAccessList = new ArrayList<>();

			accessList.forEach(entry -> {
				List<RlpType> rlpAccessListObject = new ArrayList<>();
				// add address
				rlpAccessListObject.add(RlpString.create(Numeric.hexStringToByteArray(entry.getAddress().getHexAddress())));

				// add storage keys
				List<RlpType> keyList = new ArrayList<>();
				entry.getStorageKeys().forEach(key -> {
					keyList.add(RlpString.create(Numeric.hexStringToByteArray(key)));
				});
				rlpAccessListObject.add(new RlpList(keyList));

				rlpAccessList.add(new RlpList(rlpAccessListObject));
			});

			values.add(new RlpList(rlpAccessList));
		}

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
	
	public Address getTo() {
		return to;
	}
	
	public void setTo(Address to) {
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

	public BigInteger getType() {
		return type;
	}

	public void setType(BigInteger type) {
		this.type = type;
	}

	public BigInteger getMaxPriorityFeePerGas() {
		return maxPriorityFeePerGas;
	}

	public void setMaxPriorityFeePerGas(BigInteger maxPriorityFeePerGas) {
		this.maxPriorityFeePerGas = maxPriorityFeePerGas;
	}

	public BigInteger getMaxFeePerGas() {
		return maxFeePerGas;
	}

	public void setMaxFeePerGas(BigInteger maxFeePerGas) {
		this.maxFeePerGas = maxFeePerGas;
	}

	public List<AccessListEntry> getAccessList() {
		return accessList;
	}

	public void setAccessList(List<AccessListEntry> accessList) {
		this.accessList = accessList;
	}

	public static byte[] concat(byte[]... arrays) {
		// Calculate the total length of the resulting array
		int totalLength = Arrays.stream(arrays).mapToInt(arr -> arr.length).sum();

		// Create a ByteBuffer with the total length
		ByteBuffer buffer = ByteBuffer.allocate(totalLength);

		// Put each byte array into the ByteBuffer
		for (byte[] array : arrays) {
			buffer.put(array);
		}

		// Return the underlying byte array
		return buffer.array();
	}

}

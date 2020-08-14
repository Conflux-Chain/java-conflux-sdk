package conflux.web3j.types;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.web3j.utils.Strings;

import conflux.web3j.Cfx;
import conflux.web3j.request.Call;
import conflux.web3j.response.UsedGasAndCollateral;

public class TransactionBuilder {
	
	public static final BigDecimal DEFAULT_GAS_OVERFLOW_RATIO = BigDecimal.valueOf(1.3);
	public static final BigDecimal DEFAULT_COLLATERAL_OVERFLOW_RATIO = BigDecimal.valueOf(2);
	
	private String from;
	private BigDecimal gasOverflowRatio;
	private BigDecimal collateralOverflowRatio;
	private RawTransaction tx = new RawTransaction();
	
	public TransactionBuilder(String from) {
		this(from, DEFAULT_GAS_OVERFLOW_RATIO, DEFAULT_COLLATERAL_OVERFLOW_RATIO);
	}
	
	public TransactionBuilder(String from, BigDecimal gasOverflowRatio, BigDecimal collateralOverflowRatio) {
		this.from = from;
		this.gasOverflowRatio = gasOverflowRatio;
		this.collateralOverflowRatio = collateralOverflowRatio;
	}
	
	public TransactionBuilder withFrom(String from) {
		this.from = from;
		return this;
	}
	
	public TransactionBuilder withNonce(BigInteger nonce) {
		this.tx.setNonce(nonce);
		return this;
	}
	
	public TransactionBuilder withGasPrice(BigInteger price) {
		this.tx.setGasPrice(price);
		return this;
	}
	
	public TransactionBuilder withGasLimit(BigInteger gasLimit) {
		this.tx.setGas(gasLimit);
		return this;
	}
	
	public TransactionBuilder withGasLimit(long gasLimit) {
		this.tx.setGas(BigInteger.valueOf(gasLimit));
		return this;
	}
	
	public TransactionBuilder withTo(String to) {
		this.tx.setTo(to);
		return this;
	}
	
	public TransactionBuilder withValue(BigInteger value) {
		this.tx.setValue(value);
		return this;
	}
	
	public TransactionBuilder withStorageLimit(BigInteger storageLimit) {
		this.tx.setStorageLimit(storageLimit);
		return this;
	}
	
	public TransactionBuilder withStorageLimit(long storageLimit) {
		this.tx.setStorageLimit(BigInteger.valueOf(storageLimit));
		return this;
	}
	
	public TransactionBuilder withEpochHeight(BigInteger epoch) {
		this.tx.setEpochHeight(epoch);
		return this;
	}
	
	public TransactionBuilder withEpochHeight(long epoch) {
		this.tx.setEpochHeight(BigInteger.valueOf(epoch));
		return this;
	}
	
	public TransactionBuilder withChainId(BigInteger chainId) {
		this.tx.setChainId(chainId);
		return this;
	}
	
	public TransactionBuilder withChainId(long chainId) {
		this.tx.setChainId(BigInteger.valueOf(chainId));
		return this;
	}
	
	public TransactionBuilder withData(String data) {
		this.tx.setData(data);
		return this;
	}
	
	public RawTransaction build(Cfx cfx) {
		this.estimateLimit(cfx);
		
		if (this.tx.getNonce() == null) {
			this.tx.setNonce(cfx.getNonce(this.from).sendAndGet());
		}
		
		if (this.tx.getGasPrice() == null) {
			this.tx.setGasPrice(RawTransaction.getDefaultGasPrice());
		}
		
		if (this.tx.getTo() == null) {
			this.tx.setTo("");
		}
		
		if (this.tx.getValue() == null) {
			this.tx.setValue(BigInteger.ZERO);
		}
		
		if (this.tx.getEpochHeight() == null) {
			this.tx.setEpochHeight(cfx.getEpochNumber().sendAndGet());
		}
		
		if (this.tx.getChainId() == null) {
			this.tx.setChainId(RawTransaction.getDefaultChainId());
		}
		
		return this.tx;
	}
	
	private void estimateLimit(Cfx cfx) {
		if (this.tx.getGas() != null && this.tx.getStorageLimit() != null) {
			return;
		}
		
		Call call = new Call();
		call.setFrom(this.from);
		
		String to = this.tx.getTo();
		if (!Strings.isEmpty(to)) {
			call.setTo(to);
		}
		
		BigInteger value = this.tx.getValue();
		if (value != null) {
			call.setValue(value);
		}
		
		String data = this.tx.getData();
		if (!Strings.isEmpty(data)) {
			call.setData(data);
		}
		
		UsedGasAndCollateral estimation = cfx.estimateGasAndCollateral(call).sendAndGet();
		
		if (this.tx.getGas() == null) {
			BigDecimal gasLimit = new BigDecimal(estimation.getGasUsed()).multiply(this.gasOverflowRatio);
			this.tx.setGas(gasLimit.toBigInteger());
		}
		
		if (this.tx.getStorageLimit() == null) {
			BigDecimal storageLimit = new BigDecimal(estimation.getStorageCollateralized()).multiply(this.collateralOverflowRatio);
			this.tx.setStorageLimit(storageLimit.toBigInteger());
		}
	}

}

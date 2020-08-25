package conflux.web3j;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.utils.Strings;

import com.fasterxml.jackson.annotation.JsonIgnore;

import conflux.web3j.request.Call;
import conflux.web3j.response.UsedGasAndCollateral;
import conflux.web3j.types.AddressType;
import conflux.web3j.types.RawTransaction;
import conflux.web3j.types.SendTransactionError;
import conflux.web3j.types.SendTransactionResult;
import conflux.web3j.types.TransactionBuilder;

public class Account {
	
	private Cfx cfx;
	private String address;
	private BigInteger nonce;
	
	private AccountManager am;
	private ECKeyPair ecKeyPair;
	
	private Account(Cfx cfx, String address) {
		this.cfx = cfx;
		this.address = address;
		this.nonce = cfx.getNonce(address).sendAndGet();
	}
	
	public static Account unlock(Cfx cfx, AccountManager am, String address, String password) throws Exception {
		return unlock(cfx, am, address, password, Duration.ZERO);
	}
	
	public static Account unlock(Cfx cfx, AccountManager am, String address, String password, Duration unlockTimeout) throws Exception {
		if (!am.unlock(address, password, unlockTimeout)) {
			throw new Exception("account not found in keystore");
		}
		
		Account account = new Account(cfx, address);
		account.am = am;
		
		return account;
	}
	
	public static Account create(Cfx cfx, String privateKey) {
		Credentials credentials = Credentials.create(privateKey);
		
		Account account = new Account(cfx, AddressType.User.normalize(credentials.getAddress()));
		account.ecKeyPair = credentials.getEcKeyPair();
		
		return account;
	}
	
	@JsonIgnore
	public Cfx getCfx() {
		return cfx;
	}
	
	public String getAddress() {
		return address;
	}
	
	public BigInteger getNonce() {
		return nonce;
	}
	
	public void setNonce(BigInteger nonce) {
		this.nonce = nonce;
	}
	
	public String sign(RawTransaction tx) throws Exception {
		return this.ecKeyPair == null
				? this.am.signTransaction(tx, this.address)
				: tx.sign(this.ecKeyPair);
	}
	
	public SendTransactionResult send(String signedTx) throws Exception {
		SendTransactionResult result = this.cfx.sendRawTransactionAndGet(signedTx);
		
		/*
		 * Update nonce in following cases:
		 * 1. Send transaction successfully.
		 * 2. Transaction sent multiple times due to IO error via retry mechanism,
		 * and RPC error TxAlreadyExists returned.
		 * 
		 * Generally, this is used to send multiple transactions with continuous tx nonce.
		 * So, each transaction sent to full node should be unique. When RPC error TxAlreadyExists
		 * returned, the corresponding transaction should be received by RPC server.
		 */
		if (result.getRawError() == null 
				|| result.getErrorType().equals(SendTransactionError.TxAlreadyExists)
				|| result.getErrorType().equals(SendTransactionError.InvalidNonceAlreadyUsed)) {
			this.nonce = this.nonce.add(BigInteger.ONE);
		}
		
		return result;
	}
	
	public String mustSend(String signedTx) throws Exception {
		SendTransactionResult result = this.send(signedTx);
		
		if (result.getRawError() != null) {
			throw new RpcException(result.getRawError());
		}
		
		return result.getTxHash();
	}
	
	public String mustSend(RawTransaction tx) throws Exception {
		String signedTx = this.sign(tx);
		return this.mustSend(signedTx);
	}
	
	public SendTransactionResult send(RawTransaction tx) throws Exception {
		String signedTx = this.sign(tx);
		return this.send(signedTx);
	}
	
	public String transfer(String to, BigInteger value) throws Exception {
		return this.transfer(new Option(), to, value);
	}
	
	public String transfer(Option option, String to, BigInteger value) throws Exception {
		option.apply(this.cfx);
		RawTransaction tx = RawTransaction.transfer(this.nonce, to, value, option.epochHeight);
		option.updatePriceAndChainId(tx);
		option.updateGasLimit(tx);
		return this.mustSend(tx);
	}
	
	public String deploy(String bytecodes) throws Exception {
		return this.deploy(new Option(), bytecodes);
	}
	
	public String deploy(Option option, String bytecodes) throws Exception {
		option.apply(this.cfx, this.address, "", bytecodes);
		RawTransaction tx = RawTransaction.deploy(this.nonce, option.gasLimit, option.value, option.storageLimit, option.epochHeight, bytecodes);
		option.updatePriceAndChainId(tx);
		return this.mustSend(tx);
	}
	
	public String call(String contract, String method, Type<?>... inputs) throws Exception {
		return this.call(new Option(), contract, method, inputs);
	}
	
	public String call(Option option, String contract, String method, Type<?>... inputs) throws Exception {
		String data = "";
		
		if (!Strings.isEmpty(method)) {
			Function function = new Function(method, Arrays.asList(inputs), Collections.emptyList());
			data = FunctionEncoder.encode(function);
		}
		
		return this.call(option, contract, data);
	}
	
	public String call(String contract, String data) throws Exception {
		return this.call(new Option(), contract, data);
	}
	
	public String call(Option option, String contract, String data) throws Exception {
		option.apply(this.cfx, this.address, contract, data);
		RawTransaction tx = RawTransaction.create(this.nonce, option.gasLimit, contract, option.value, option.storageLimit, option.epochHeight, data);
		option.updatePriceAndChainId(tx);
		return this.mustSend(tx);
	}
	
	public void waitForNonceUpdated() throws InterruptedException {
		this.cfx.waitForNonce(this.address, this.nonce);
	}
	
	public void waitForNonceUpdated(long intervalMillis) throws InterruptedException {
		this.cfx.waitForNonce(this.address, this.nonce, intervalMillis);
	}
	
	public static class Option {
		private BigInteger gasPrice;
		private BigInteger gasLimit;
		private BigInteger storageLimit;
		private BigInteger value = BigInteger.ZERO;
		private BigInteger epochHeight;
		private BigInteger chainId;
		
		private BigDecimal gasOverflowRatio;
		private BigDecimal collateralOverflowRatio;
		
		public Option() {
			this(TransactionBuilder.DEFAULT_GAS_OVERFLOW_RATIO, TransactionBuilder.DEFAULT_COLLATERAL_OVERFLOW_RATIO);
		}
		
		public Option(BigDecimal gasOverflowRatio, BigDecimal collateralOverflowRatio) {
			this.gasOverflowRatio = gasOverflowRatio;
			this.collateralOverflowRatio = collateralOverflowRatio;
		}
		
		public Option withGasPrice(BigInteger price) {
			this.gasPrice = price;
			return this;
		}
		
		public Option withGasLimit(BigInteger gasLimit) {
			this.gasLimit = gasLimit;
			return this;
		}
		
		public Option withGasLimit(long gasLimit) {
			this.gasLimit = BigInteger.valueOf(gasLimit);
			return this;
		}
		
		public Option withStorageLimit(BigInteger storageLimit) {
			this.storageLimit = storageLimit;
			return this;
		}
		
		public Option withStorageLimit(long storageLimit) {
			this.storageLimit = BigInteger.valueOf(storageLimit);
			return this;
		}
		
		public Option withValue(BigInteger value) {
			this.value = value;
			return this;
		}
		
		public Option withEpochHeight(BigInteger epoch) {
			this.epochHeight = epoch;
			return this;
		}
		
		public Option withEpochHeight(long epoch) {
			this.epochHeight = BigInteger.valueOf(epoch);
			return this;
		}
		
		public Option withChainId(BigInteger chainId) {
			this.chainId = chainId;
			return this;
		}
		
		public Option withChainId(long chainId) {
			this.chainId = BigInteger.valueOf(chainId);
			return this;
		}
		
		private void apply(Cfx cfx) {
			if (this.epochHeight == null) {
				this.epochHeight = cfx.getEpochNumber().sendAndGet();
			}
		}
		
		private void apply(Cfx cfx, String from, String to, String data) {
			if (this.epochHeight == null) {
				this.epochHeight = cfx.getEpochNumber().sendAndGet();
			}
			
			if (this.gasLimit != null && this.storageLimit != null) {
				return;
			}
			
			Call call = new Call();
			
			if (!Strings.isEmpty(from)) {
				call.setFrom(from);
			}
			
			if (!Strings.isEmpty(to)) {
				call.setTo(to);
			}
			
			call.setValue(this.value);
			
			if (!Strings.isEmpty(data)) {
				call.setData(data);
			}
			
			UsedGasAndCollateral estimation = cfx.estimateGasAndCollateral(call).sendAndGet();
			
			if (this.gasLimit == null) {
				this.gasLimit = new BigDecimal(estimation.getGasUsed()).multiply(this.gasOverflowRatio).toBigInteger();
			}
			
			if (this.storageLimit == null) {
				this.storageLimit = new BigDecimal(estimation.getStorageCollateralized()).multiply(this.collateralOverflowRatio).toBigInteger();
			}
		}
		
		private void updatePriceAndChainId(RawTransaction tx) {
			if (this.gasPrice != null) {
				tx.setGasPrice(this.gasPrice);
			}
			
			if (this.chainId != null) {
				tx.setChainId(this.chainId);
			}
		}

		private void updateGasLimit(RawTransaction tx) {
			if (this.gasLimit != null) {
				tx.setGas(this.gasLimit);
			}
		}
	}

}

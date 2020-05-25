package conflux.web3j;

import java.math.BigInteger;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;

import com.fasterxml.jackson.annotation.JsonIgnore;

import conflux.web3j.types.AddressType;
import conflux.web3j.types.RawTransaction;
import conflux.web3j.types.SendTransactionError;
import conflux.web3j.types.SendTransactionResult;

public class Account {
	
	public static BigInteger DefaultStorageLimit = BigInteger.valueOf(10000);
	
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
		BigInteger currentEpoch = this.cfx.getEpochNumber().sendAndGet();
		RawTransaction tx = RawTransaction.transfer(this.nonce, to, value, currentEpoch);
		return this.mustSend(tx);
	}
	
	public String deploy(BigInteger gasLimit, String bytecodes) throws Exception {
		return this.deploy(gasLimit, BigInteger.ZERO, bytecodes);
	}
	
	public String deploy(BigInteger gasLimit, BigInteger value, String bytecodes) throws Exception {
		BigInteger currentEpoch = this.cfx.getEpochNumber().sendAndGet();
		RawTransaction tx = RawTransaction.deploy(this.nonce, gasLimit, value, DefaultStorageLimit, currentEpoch, bytecodes);
		return this.mustSend(tx);
	}
	
	public String call(String contract, BigInteger gasLimit, String method, Type<?>... inputs) throws Exception {
		return this.call(contract, gasLimit, BigInteger.ZERO, method, inputs);
	}
	
	public String call(String contract, BigInteger gasLimit, BigInteger value, String method, Type<?>... inputs) throws Exception {
		String data = "";
		
		if (method != null && !method.isEmpty()) {
			Function function = new Function(method, Arrays.asList(inputs), Collections.emptyList());
			data = FunctionEncoder.encode(function);
		}
		
		return this.call(contract, gasLimit, value, data);
	}
	
	public String call(String contract, BigInteger gasLimit, String data) throws Exception {
		return this.call(contract, gasLimit, BigInteger.ZERO, data);
	}
	
	public String call(String contract, BigInteger gasLimit, BigInteger value, String data) throws Exception {
		BigInteger currentEpoch = this.cfx.getEpochNumber().sendAndGet();
		RawTransaction tx = RawTransaction.create(this.nonce, gasLimit, contract, value, DefaultStorageLimit, currentEpoch, data);
		return this.mustSend(tx);
	}
	
	public void waitForNonceUpdated() throws InterruptedException {
		this.cfx.waitForNonce(this.address, this.nonce);
	}
	
	public void waitForNonceUpdated(long intervalMillis) throws InterruptedException {
		this.cfx.waitForNonce(this.address, this.nonce, intervalMillis);
	}

}

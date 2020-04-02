package conflux.web3j;

import java.math.BigInteger;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.crypto.Credentials;

public class Account {
	
	public static BigInteger DefaultStorageLimit = BigInteger.valueOf(100000000);
	
	private Cfx cfx;
	private String address;
	private BigInteger nonce;
	
	private AccountManager am;
	private Credentials credentials;
	
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
		
		Account account = new Account(cfx, credentials.getAddress());
		account.credentials = credentials;
		
		return account;
	}
	
	public BigInteger getNonce() {
		return nonce;
	}
	
	public String send(RawTransaction tx) throws Exception {
		String signedTx = this.credentials == null
				? this.am.signTransaction(tx, this.address)
				: tx.sign(this.credentials.getEcKeyPair());
		String txHash = this.cfx.sendRawTransaction(signedTx).sendAndGet();
		
		if (txHash == null || txHash.isEmpty()) {
			throw new Exception("failed to send transaction, tx hash is null or empty");
		}
		
		this.nonce = this.nonce.add(BigInteger.ONE);
		
		return txHash;
	}
	
	public String transfer(String to, BigInteger value) throws Exception {
		BigInteger currentEpoch = this.cfx.getEpochNumber().sendAndGet();
		RawTransaction tx = RawTransaction.transfer(this.nonce, to, value, currentEpoch);
		return this.send(tx);
	}
	
	public String deploy(BigInteger gasLimit, String bytecodes) throws Exception {
		return this.deploy(gasLimit, BigInteger.ZERO, bytecodes);
	}
	
	public String deploy(BigInteger gasLimit, BigInteger value, String bytecodes) throws Exception {
		BigInteger currentEpoch = this.cfx.getEpochNumber().sendAndGet();
		RawTransaction tx = RawTransaction.deploy(this.nonce, gasLimit, value, DefaultStorageLimit, currentEpoch, bytecodes);
		return this.send(tx);
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
		return this.send(tx);
	}
	
	public void waitForNonceUpdated() throws InterruptedException {
		this.cfx.waitForNonce(this.address, this.nonce);
	}
	
	public void waitForNonceUpdated(long intervalMillis) throws InterruptedException {
		this.cfx.waitForNonce(this.address, this.nonce, intervalMillis);
	}

}

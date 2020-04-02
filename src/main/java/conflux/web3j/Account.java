package conflux.web3j;

import java.math.BigInteger;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;

public class Account {
	
	private Cfx cfx;
	private AccountManager am;
	private Credentials credentials;
	private String address;
	
	private BigInteger gasPrice = CfxUnit.DEFAULT_GAS_PRICE;
	private BigInteger nonce;
	
	private Account(Cfx cfx, String address) {
		this.cfx = cfx;
		this.address = address;
		this.nonce = cfx.getTransactionCount(address).sendAndGet();
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
	
	public BigInteger getGasPrice() {
		return gasPrice;
	}
	
	public Account withGasPrice(BigInteger gasPrice) {
		this.gasPrice = gasPrice;
		return this;
	}
	
	public String send(RawTransaction tx) throws Exception {
		String signedTx = this.credentials == null
				? this.am.signTransaction(tx, this.address)
				: AccountManager.signTransaction(tx, this.credentials);
		String txHash = this.cfx.sendRawTransaction(signedTx).sendAndGet();
		
		if (txHash == null || txHash.isEmpty()) {
			throw new Exception("failed to send transaction, tx hash is null or empty");
		}
		
		this.nonce = this.nonce.add(BigInteger.ONE);
		
		return txHash;
	}
	
	public String transfer(String to, BigInteger value) throws Exception {
		RawTransaction tx = RawTransaction.createEtherTransaction(this.nonce, this.gasPrice, CfxUnit.DEFAULT_GAS_LIMIT, to, value);
		return this.send(tx);
	}
	
	public String deploy(BigInteger gasLimit, String bytecodes) throws Exception {
		return this.deploy(gasLimit, BigInteger.ZERO, bytecodes);
	}
	
	public String deploy(BigInteger gasLimit, BigInteger value, String bytecodes) throws Exception {
		RawTransaction tx = RawTransaction.createContractTransaction(this.nonce, this.gasPrice, gasLimit, value, bytecodes);
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
		
		RawTransaction tx = RawTransaction.createTransaction(this.nonce, this.gasPrice, gasLimit, contract, value, data);
		return this.send(tx);
	}
	
	public String call(String contract, BigInteger gasLimit, String data) throws Exception {
		return this.call(contract, gasLimit, BigInteger.ZERO, data);
	}
	
	public String call(String contract, BigInteger gasLimit, BigInteger value, String data) throws Exception {
		RawTransaction tx = RawTransaction.createTransaction(this.nonce, this.gasPrice, gasLimit, contract, value, data);
		return this.send(tx);
	}
	
	public void waitForNonceUpdated() throws InterruptedException {
		this.cfx.waitForNonce(this.address, this.nonce);
	}
	
	public void waitForNonceUpdated(long intervalMillis) throws InterruptedException {
		this.cfx.waitForNonce(this.address, this.nonce, intervalMillis);
	}

}

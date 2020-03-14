package conflux.web3j.contract;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.crypto.RawTransaction;

import conflux.web3j.AccountManager;
import conflux.web3j.Cfx;
import conflux.web3j.CfxUnit;
import conflux.web3j.RpcException;

public class ContractExecutor {
	
	private Cfx cfx;
	private AccountManager am;
	private String from;
	private String to;
	private BigInteger nonce;
	
	private String password;
	private BigInteger gasPrice = CfxUnit.DEFAULT_GAS_PRICE;
	
	public ContractExecutor(Cfx cfx, AccountManager am, String userAddress, String contractAddress) throws RpcException {
		this(cfx, am, userAddress, cfx.getTransactionCount(userAddress).sendAndGet(), contractAddress);
	}
	
	public ContractExecutor(Cfx cfx, AccountManager am, String userAddress, BigInteger nonce, String contractAddress) {
		this.cfx = cfx;
		this.am = am;
		this.from = userAddress;
		this.to = contractAddress;
		this.nonce = nonce;
	}
	
	public ContractExecutor buildPassword(String password) {
		this.password = password;
		return this;
	}
	
	public ContractExecutor buildGasPrice(BigInteger price) {
		this.gasPrice = price;
		return this;
	}
	
	public BigInteger getNonce() {
		return this.nonce;
	}
	
	public void waitForNonceUpdated() throws InterruptedException {
		this.cfx.waitForNonce(this.from, this.nonce);
	}
	
	public void waitForNonceUpdated(long intervalMillis) throws InterruptedException {
		this.cfx.waitForNonce(this.from, this.nonce, intervalMillis);
	}
	
	public String execute(BigInteger gasLimit, String method, Type<?>... inputs) throws Exception {
		return this.execute(gasLimit, BigInteger.ZERO, method, inputs);
	}
	
	public String execute(BigInteger gasLimit, BigInteger value, String method, Type<?>... inputs) throws Exception {
		RawTransaction tx;
		
		if (method == null || method.isEmpty()) {
			tx = RawTransaction.createEtherTransaction(this.nonce, this.gasPrice, gasLimit, this.to, value);
		} else {
			Function function = new Function(method, Arrays.asList(inputs), Collections.emptyList());
			String data = FunctionEncoder.encode(function);
			tx = RawTransaction.createTransaction(this.nonce, this.gasPrice, gasLimit, this.to, value, data);
		}
		
		String signedTx = this.password == null
				? this.am.signTransaction(tx, this.from)
				: this.am.signTransaction(tx, this.from, this.password);
		
		String txHash = this.cfx.sendRawTransaction(signedTx).sendAndGet();
		
		this.nonce = this.nonce.add(BigInteger.ONE);
		
		return txHash;
	}

}

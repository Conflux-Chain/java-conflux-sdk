package conflux.web3j.contract;

import java.math.BigInteger;

import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.generated.Uint256;

import conflux.web3j.Account;
import conflux.web3j.RpcException;

public class ERC777Executor {
	
	private Account account;
	private String contract;
	
	public ERC777Executor(Account account, String erc777Address) throws RpcException {
		this.account = account;
		this.contract = erc777Address;
	}
	
	public String send(BigInteger gasLimit, BigInteger storageLimit, String recipient, BigInteger amount, byte[] data) throws Exception {
		return this.account.call(this.contract, gasLimit, storageLimit, "send", new Address(recipient), new Uint256(amount), new DynamicBytes(data));
	}
	
	public String burn(BigInteger gasLimit, BigInteger storageLimit, BigInteger amount, byte[] data) throws Exception {
		return this.account.call(this.contract, gasLimit, storageLimit, "burn", new Uint256(amount), new DynamicBytes(data));
	}
	
	public String authorizeOperator(BigInteger gasLimit, BigInteger storageLimit, String operator) throws Exception {
		return this.account.call(this.contract, gasLimit, storageLimit, "authorizeOperator", new Address(operator));
	}
	
	public String revokeOperator(BigInteger gasLimit, BigInteger storageLimit, String operator) throws Exception {
		return this.account.call(this.contract, gasLimit, storageLimit, "revokeOperator", new Address(operator));
	}
	
	public String operatorSend(BigInteger gasLimit, BigInteger storageLimit, String sender, String recipient, BigInteger amount, byte[] data, byte[] operatorData) throws Exception {
		return this.account.call(this.contract, gasLimit, storageLimit, "operatorSend", new Address(sender), new Address(recipient), new Uint256(amount), new DynamicBytes(data), new DynamicBytes(operatorData));
	}
	
	public String operatorBurn(BigInteger gasLimit, BigInteger storageLimit, String account, BigInteger amount, byte[] data, byte[] operatorData) throws Exception {
		return this.account.call(this.contract, gasLimit, storageLimit, "operatorBurn", new Address(account), new Uint256(amount), new DynamicBytes(data), new DynamicBytes(operatorData));
	}

}

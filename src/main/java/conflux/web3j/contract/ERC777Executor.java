package conflux.web3j.contract;

import java.math.BigInteger;

import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.generated.Uint256;

import conflux.web3j.Account;
import conflux.web3j.RpcException;
import conflux.web3j.Account.Option;

public class ERC777Executor {
	
	private Account account;
	private String contract;
	
	public ERC777Executor(Account account, String erc777Address) throws RpcException {
		this.account = account;
		this.contract = erc777Address;
	}
	
	public String send(Option option, String recipient, BigInteger amount, byte[] data) throws Exception {
		return this.account.call(option, this.contract, "send", new Address(recipient), new Uint256(amount), new DynamicBytes(data));
	}
	
	public String burn(Option option, BigInteger amount, byte[] data) throws Exception {
		return this.account.call(option, this.contract, "burn", new Uint256(amount), new DynamicBytes(data));
	}
	
	public String authorizeOperator(Option option, String operator) throws Exception {
		return this.account.call(option, this.contract, "authorizeOperator", new Address(operator));
	}
	
	public String revokeOperator(Option option, String operator) throws Exception {
		return this.account.call(option, this.contract, "revokeOperator", new Address(operator));
	}
	
	public String operatorSend(Option option, String sender, String recipient, BigInteger amount, byte[] data, byte[] operatorData) throws Exception {
		return this.account.call(option, this.contract, "operatorSend", new Address(sender), new Address(recipient), new Uint256(amount), new DynamicBytes(data), new DynamicBytes(operatorData));
	}
	
	public String operatorBurn(Option option, String account, BigInteger amount, byte[] data, byte[] operatorData) throws Exception {
		return this.account.call(option, this.contract, "operatorBurn", new Address(account), new Uint256(amount), new DynamicBytes(data), new DynamicBytes(operatorData));
	}

}

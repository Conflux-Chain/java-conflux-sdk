package conflux.web3j.contract;

import java.math.BigInteger;

import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.generated.Uint256;

import conflux.web3j.AccountManager;
import conflux.web3j.Cfx;
import conflux.web3j.RpcException;

public class ERC777Executor extends ContractExecutor {
	
	public ERC777Executor(Cfx cfx, AccountManager am, String userAddress, String erc777Address) throws RpcException {
		super(cfx, am, userAddress, erc777Address);
	}
	
	public ERC777Executor(Cfx cfx, AccountManager am, String userAddress, BigInteger nonce, String erc777Address) {
		super(cfx, am, userAddress, nonce, erc777Address);
	}
	
	public String send(BigInteger gasLimit, String recipient, BigInteger amount, byte[] data) throws Exception {
		return this.execute(gasLimit, "send", new Address(recipient), new Uint256(amount), new DynamicBytes(data));
	}
	
	public String burn(BigInteger gasLimit, BigInteger amount, byte[] data) throws Exception {
		return this.execute(gasLimit, "burn", new Uint256(amount), new DynamicBytes(data));
	}
	
	public String authorizeOperator(BigInteger gasLimit, String operator) throws Exception {
		return this.execute(gasLimit, "authorizeOperator", new Address(operator));
	}
	
	public String revokeOperator(BigInteger gasLimit, String operator) throws Exception {
		return this.execute(gasLimit, "revokeOperator", new Address(operator));
	}
	
	public String operatorSend(BigInteger gasLimit, String sender, String recipient, BigInteger amount, byte[] data, byte[] operatorData) throws Exception {
		return this.execute(gasLimit, "operatorSend", new Address(sender), new Address(recipient), new Uint256(amount), new DynamicBytes(data), new DynamicBytes(operatorData));
	}
	
	public String operatorBurn(BigInteger gasLimit, String account, BigInteger amount, byte[] data, byte[] operatorData) throws Exception {
		return this.execute(gasLimit, "operatorBurn", new Address(account), new Uint256(amount), new DynamicBytes(data), new DynamicBytes(operatorData));
	}

}

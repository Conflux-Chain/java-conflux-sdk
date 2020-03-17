package conflux.web3j.contract;

import java.math.BigInteger;

import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.generated.Uint256;

import conflux.web3j.AccountManager;
import conflux.web3j.Cfx;
import conflux.web3j.RpcException;

public class ERC20Executor extends ContractExecutor {
	
	public ERC20Executor(Cfx cfx, AccountManager am, String userAddress, String erc20Address) throws RpcException {
		super(cfx, am, userAddress, erc20Address);
	}
	
	public ERC20Executor(Cfx cfx, AccountManager am, String userAddress, BigInteger nonce, String erc20Address) {
		super(cfx, am, userAddress, nonce, erc20Address);
	}
	
	public String transfer(BigInteger gasLimit, String recipient, BigInteger amount) throws Exception {
		return this.execute(gasLimit, "transfer", new Address(recipient), new Uint256(amount));
	}
	
	public String approve(BigInteger gasLimit, String spender, BigInteger amount) throws Exception {
		return this.execute(gasLimit, "approve", new Address(spender), new Uint256(amount));
	}
	
	public String transferFrom(BigInteger gasLimit, String sender, String recipient, BigInteger amount) throws Exception {
		return this.execute(gasLimit, "transferFrom", new Address(sender), new Address(recipient), new Uint256(amount));
	}

}

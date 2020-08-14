package conflux.web3j.contract;

import java.math.BigInteger;

import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.generated.Uint256;

import conflux.web3j.Account;
import conflux.web3j.Account.Option;

public class ERC20Executor {
	
	private Account account;
	private String contract;
	
	public ERC20Executor(Account account, String erc20Address) {
		this.account = account;
		this.contract = erc20Address;
	}
	
	public String transfer(Option option, String recipient, BigInteger amount) throws Exception {
		return this.account.call(option, this.contract, "transfer", new Address(recipient), new Uint256(amount));
	}
	
	public String approve(Option option, String spender, BigInteger amount) throws Exception {
		return this.account.call(option, this.contract, "approve", new Address(spender), new Uint256(amount));
	}
	
	public String transferFrom(Option option, String sender, String recipient, BigInteger amount) throws Exception {
		return this.account.call(option, this.contract, "transferFrom", new Address(sender), new Address(recipient), new Uint256(amount));
	}

}

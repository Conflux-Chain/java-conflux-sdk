package conflux.web3j.contract;

import java.math.BigInteger;

import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.generated.Uint256;

import conflux.web3j.Cfx;
import conflux.web3j.RpcException;
import conflux.web3j.contract.abi.DecodeUtil;

public class ERC20Call extends ContractCall {

	public ERC20Call(Cfx cfx, String erc20Address) {
		super(cfx, erc20Address);
	}
	
	public BigInteger totalSupply() throws RpcException {
		String encodedResult = this.call("totalSupply").sendAndGet();
		return DecodeUtil.decode(encodedResult, Uint256.class);
	}
	
	public BigInteger balanceOf(String account) throws RpcException {
		String encodedResult = this.call("balanceOf", new Address(account)).sendAndGet();
		return DecodeUtil.decode(encodedResult, Uint256.class);
	}
	
	public BigInteger allowance(String owner, String spender) throws RpcException {
		String encodedResult = this.call("allowance", new Address(owner), new Address(spender)).sendAndGet();
		return DecodeUtil.decode(encodedResult, Uint256.class);
	}

}

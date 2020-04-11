package conflux.web3j.contract;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;

import conflux.web3j.Cfx;
import conflux.web3j.RpcException;
import conflux.web3j.contract.abi.DecodeUtil;

public class ERC777Call extends ContractCall {
	
	private static final TypeReference<DynamicArray<Address>> TYPE_DYNAMIC_ARRAY_ADDRESS = new TypeReference<DynamicArray<Address>>() {};

	public ERC777Call(Cfx cfx, String erc777Address) {
		super(cfx, erc777Address);
	}
	
	public String name() throws RpcException {
		String encodedResult = this.call("name").sendAndGet();
		return DecodeUtil.decode(encodedResult, Utf8String.class);
	}
	
	public String symbol() throws RpcException {
		String encodedResult = this.call("symbol").sendAndGet();
		return DecodeUtil.decode(encodedResult, Utf8String.class);
	}
	
	public BigInteger granularity() throws RpcException {
		String encodedResult = this.call("granularity").sendAndGet();
		return DecodeUtil.decode(encodedResult, Uint256.class);
	}
	
	public BigInteger totalSupply() throws RpcException {
		String encodedResult = this.call("totalSupply").sendAndGet();
		return DecodeUtil.decode(encodedResult, Uint256.class);
	}
	
	public BigInteger balanceOf(String owner) throws RpcException {
		String encodedResult = this.call("balanceOf", new Address(owner)).sendAndGet();
		return DecodeUtil.decode(encodedResult, Uint256.class);
	}
	
	public boolean isOperatorFor(String operator, String tokenHolder) throws RpcException {
		String encodedResult = this.call("isOperatorFor", new Address(operator), new Address(tokenHolder)).sendAndGet();
		return DecodeUtil.decode(encodedResult, Bool.class);
	}
	
	public List<String> defaultOperators() throws RpcException {
		String encodedResult = this.call("defaultOperators").sendAndGet();
		List<Address> operators = DecodeUtil.decode(encodedResult, TYPE_DYNAMIC_ARRAY_ADDRESS);
		return operators.stream()
				.map(address -> address.getValue())
				.collect(Collectors.toList());
	}

}

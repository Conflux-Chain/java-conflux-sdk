package conflux.web3j.contract;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import conflux.web3j.request.LogFilter;
import conflux.web3j.response.Log;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.StaticArray;
import org.web3j.abi.datatypes.Type;

import conflux.web3j.Cfx;
import conflux.web3j.Request;
import conflux.web3j.types.Address;
import conflux.web3j.contract.abi.DecodeUtil;
import conflux.web3j.request.Call;
import conflux.web3j.request.Epoch;
import conflux.web3j.response.StringResponse;
import conflux.web3j.response.UsedGasAndCollateral;

public class ContractCall {
	
	private Call call = new Call();
	
	private Cfx cfx;
	private Epoch epoch;
	
	public ContractCall(Cfx cfx, Address contractAddress) {
		this.cfx = cfx;
		this.call.setTo(contractAddress);
	}
	
	public ContractCall buildFrom(Address from) {
		this.call.setFrom(from);
		BigInteger nonce = this.epoch == null
				? this.cfx.getNonce(from).sendAndGet()
				: this.cfx.getNonce(from, this.epoch).sendAndGet();
		this.call.setNonce(nonce);
		return this;
	}
	
	public ContractCall buildGasPrice(BigInteger price) {
		this.call.setGasPrice(price);
		return this;
	}
	
	public ContractCall buildGasLimit(BigInteger gas) {
		this.call.setGas(gas);
		return this;
	}
	
	public ContractCall buildValue(BigInteger value) {
		this.call.setValue(value);
		return this;
	}
	
	public ContractCall buildEpoch(Epoch epoch) {
		this.epoch = epoch;
		return this;
	}
	
	public Request<UsedGasAndCollateral, UsedGasAndCollateral.Response> estimateGasAndCollateral(String method, Type<?>... args) {
		this.buildData(method, args);
		
		return this.epoch == null
				? this.cfx.estimateGasAndCollateral(this.call)
				: this.cfx.estimateGasAndCollateral(this.call, this.epoch);
	}
	
	private void buildData(String method, Type<?>... args) {
		if (method == null || method.isEmpty()) {
			this.call.setData(null);
			return;
		}
		
		Function function = new Function(method, Arrays.asList(args), Collections.emptyList());
		String data = FunctionEncoder.encode(function);
		this.call.setData(data);
	}
	
	public Request<String, StringResponse> call(String method, Type<?>... args) {
		this.buildData(method, args);
		
		return this.epoch == null
				? this.cfx.call(this.call)
				: this.cfx.call(this.call, this.epoch);
	}

	public <D, T extends Type<D>> D callAndGet(Class<T> returnType, String method, Type<?>... args) {
		String rawData = this.call(method, args).sendAndGet();
		return DecodeUtil.decode(rawData, returnType);
	}

	public <T extends Type<?>> List<T> callAndGet(TypeReference<DynamicArray<T>> returnType, String method, Type<?>... args) {
		String rawData = this.call(method, args).sendAndGet();
		return DecodeUtil.decode(rawData, returnType);
	}

	public <T extends Type<?>> List<T> callAndGet(TypeReference.StaticArrayTypeReference<StaticArray<T>> returnType, String method, Type<?>... args) {
		String rawData = this.call(method, args).sendAndGet();
		return DecodeUtil.decode(rawData, returnType);
	}

	public List<Log> getLogs(LogFilter filter) {
		return this.cfx.getLogs(filter).sendAndGet();
	}
}

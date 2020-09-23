package conflux.web3j.contract;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;

import conflux.web3j.HasValue;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;

import conflux.web3j.Cfx;
import conflux.web3j.Request;
import conflux.web3j.request.Call;
import conflux.web3j.request.Epoch;
import conflux.web3j.response.StringResponse;
import conflux.web3j.response.UsedGasAndCollateral;
import org.web3j.protocol.core.Response;
import conflux.web3j.contract.abi.DecodeUtil;

public class ContractCall {
	
	private Call call = new Call();
	
	private Cfx cfx;
	private Epoch epoch;
	
	public ContractCall(Cfx cfx, String contractAddress) {
		this.cfx = cfx;
		this.call.setTo(contractAddress);
	}
	
	public ContractCall buildFrom(String from) {
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



}

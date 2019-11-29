package conflux.web3j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.web3j.protocol.Web3jService;
import org.web3j.protocol.core.Request;

import conflux.web3j.request.Call;
import conflux.web3j.request.Epoch;
import conflux.web3j.request.LogFilter;
import conflux.web3j.response.BigIntResponse;
import conflux.web3j.response.BlockResponse;
import conflux.web3j.response.BlockSummaryResponse;
import conflux.web3j.response.BlocksResponse;
import conflux.web3j.response.LogsResponse;
import conflux.web3j.response.ReceiptResponse;
import conflux.web3j.response.StringResponse;
import conflux.web3j.response.TransactionResponse;

/**
 * JSON-RPC Request object building factory.
 */
public class Web3j implements Cfx {
	private Web3jService service;
	
	public Web3j(Web3jService service) {
		this.service = service;
	}
	
	public void shutdown() throws IOException {
		this.service.close();
	}

	@Override
	public Request<?, BigIntResponse> getGasPrice() {
		return new Request<>("cfx_gasPrice", Collections.<String>emptyList(), this.service, BigIntResponse.class);
	}

	@Override
	public Request<?, BigIntResponse> getEpochNumber(Epoch... epoch) {
		List<String> args;
		
		if (epoch.length == 0) {
			args = Collections.emptyList();
		} else {
			args = Arrays.asList(epoch[0].getValue());
		}
		
		return new Request<>("cfx_epochNumber", args, this.service, BigIntResponse.class);
	}

	@Override
	public Request<?, BigIntResponse> getBalance(String address, Epoch... epoch) {
		List<String> args = new ArrayList<String>();
		args.add(address);
		
		if (epoch.length > 0) {
			args.add(epoch[0].getValue());
		}
		
		return new Request<>("cfx_getBalance", args, this.service, BigIntResponse.class);
	}

	@Override
	public Request<?, StringResponse> getCode(String address, Epoch... epoch) {
		List<String> args = new ArrayList<String>();
		args.add(address);
		
		if (epoch.length > 0) {
			args.add(epoch[0].getValue());
		}
		
		return new Request<>("cfx_getCode", args, this.service, StringResponse.class);
	}
	
	@Override
	public Request<?, BlockSummaryResponse> getBlockSummaryByHash(String blockHash) {
		return new Request<>("cfx_getBlockByHash", Arrays.asList(blockHash, false), this.service, BlockSummaryResponse.class);
	}

	@Override
	public Request<?, BlockResponse> getBlockByHash(String blockHash) {
		return new Request<>("cfx_getBlockByHash", Arrays.asList(blockHash, true), this.service, BlockResponse.class);
	}

	@Override
	public Request<?, BlockSummaryResponse> getBlockSummaryByEpoch(Epoch epoch) {
		return new Request<>("cfx_getBlockByEpochNumber", Arrays.asList(epoch.getValue(), false), this.service, BlockSummaryResponse.class);
	}
	
	@Override
	public Request<?, BlockResponse> getBlockByEpoch(Epoch epoch) {
		return new Request<>("cfx_getBlockByEpochNumber", Arrays.asList(epoch.getValue(), true), this.service, BlockResponse.class);
	}

	@Override
	public Request<?, StringResponse> getBestBlockHash() {
		return new Request<>("cfx_getBestBlockHash", Collections.<String>emptyList(), this.service, StringResponse.class);
	}

	@Override
	public Request<?, BigIntResponse> getTransactionCount(String address, Epoch... epoch) {
		List<String> args = new ArrayList<String>();
		args.add(address);
		
		if (epoch.length > 0) {
			args.add(epoch[0].getValue());
		}
		
		return new Request<>("cfx_getTransactionCount", args, this.service, BigIntResponse.class);
	}

	@Override
	public Request<?, StringResponse> sendRawTransaction(String hexEncoded) {
		return new Request<>("cfx_sendRawTransaction", Arrays.asList(hexEncoded), this.service, StringResponse.class);
	}

	@Override
	public Request<?, StringResponse> call(Call request, Epoch... epoch) {
		List<Object> args = new ArrayList<Object>();
		args.add(request);
		
		if (epoch.length > 0) {
			args.add(epoch[0].getValue());
		}
		
		return new Request<>("cfx_call", args, this.service, StringResponse.class);
	}

	@Override
	public Request<?, LogsResponse> getLogs(LogFilter filter) {
		return new Request<>("cfx_getLogs", Arrays.asList(filter), this.service, LogsResponse.class);
	}

	@Override
	public Request<?, TransactionResponse> getTransactionByHash(String txHash) {
		return new Request<>("cfx_getTransactionByHash", Arrays.asList(txHash), this.service, TransactionResponse.class);
	}

	@Override
	public Request<?, BigIntResponse> estimateGas(Call request, Epoch... epoch) {
		List<Object> args = new ArrayList<Object>();
		args.add(request);
		
		if (epoch.length > 0) {
			args.add(epoch[0].getValue());
		}
		
		return new Request<>("cfx_estimateGas", args, this.service, BigIntResponse.class);
	}

	@Override
	public Request<?, BlocksResponse> getBlocksByEpoch(Epoch epoch) {
		return new Request<>("cfx_getBlocksByEpoch", Arrays.asList(epoch.getValue()), this.service, BlocksResponse.class);
	}

	@Override
	public Request<?, ReceiptResponse> getTransactionReceipt(String txHash) {
		return new Request<>("cfx_getTransactionReceipt", Arrays.asList(txHash), this.service, ReceiptResponse.class);
	}
}

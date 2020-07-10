package conflux.web3j;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.web3j.protocol.Web3jService;

import conflux.web3j.request.Call;
import conflux.web3j.request.Epoch;
import conflux.web3j.request.LogFilter;
import conflux.web3j.response.BigIntResponse;
import conflux.web3j.response.Block;
import conflux.web3j.response.BlockSummary;
import conflux.web3j.response.Log;
import conflux.web3j.response.Receipt;
import conflux.web3j.response.StringResponse;
import conflux.web3j.response.Transaction;
import conflux.web3j.response.UsedGasAndCollateral;

/**
 * JSON-RPC Request object building factory.
 */
class Web3j implements Cfx {
	private Web3jService service;
	private int retry;
	private long intervalMillis;
	
	public Web3j(Web3jService service) {
		this(service, 0, 0);
	}
	
	public Web3j(Web3jService service, int retry, long intervalMillis) {
		this.service = service;
		this.retry = retry;
		this.intervalMillis = intervalMillis;
	}
	
	@Override
	public void close() throws IOException {
		this.service.close();
	}
	
	public void shutdown() throws IOException {
		this.service.close();
	}

	@Override
	public Request<BigInteger, BigIntResponse> getGasPrice() {
		return new Request<>(this.service, "cfx_gasPrice", BigIntResponse.class)
				.withRetry(this.retry, this.intervalMillis);
	}

	@Override
	public Request<BigInteger, BigIntResponse> getEpochNumber(Epoch... epoch) {
		if (epoch.length == 0) {
			return new Request<>(this.service, "cfx_epochNumber", BigIntResponse.class)
					.withRetry(this.retry, this.intervalMillis);
		} else {
			return new Request<>(this.service, "cfx_epochNumber", BigIntResponse.class, epoch[0].getValue())
					.withRetry(this.retry, this.intervalMillis);
		}
	}

	@Override
	public Request<BigInteger, BigIntResponse> getBalance(String address, Epoch... epoch) {
		if (epoch.length == 0) {
			return new Request<>(this.service, "cfx_getBalance", BigIntResponse.class, address)
					.withRetry(this.retry, this.intervalMillis);
		} else {
			return new Request<>(this.service, "cfx_getBalance", BigIntResponse.class, address, epoch[0].getValue())
					.withRetry(this.retry, this.intervalMillis);
		}
	}

	@Override
	public Request<String, StringResponse> getCode(String address, Epoch... epoch) {
		if (epoch.length == 0) {
			return new Request<>(this.service, "cfx_getCode", StringResponse.class, address)
					.withRetry(this.retry, this.intervalMillis);
		} else {
			return new Request<>(this.service, "cfx_getCode", StringResponse.class, address, epoch[0].getValue())
					.withRetry(this.retry, this.intervalMillis);
		}
	}
	
	@Override
	public Request<Optional<BlockSummary>, BlockSummary.Response> getBlockSummaryByHash(String blockHash) {
		return new Request<>(this.service, "cfx_getBlockByHash", BlockSummary.Response.class, blockHash, false)
				.withRetry(this.retry, this.intervalMillis);
	}

	@Override
	public Request<Optional<Block>, Block.Response> getBlockByHash(String blockHash) {
		return new Request<>(this.service, "cfx_getBlockByHash", Block.Response.class, blockHash, true)
				.withRetry(this.retry, this.intervalMillis);
	}

	@Override
	public Request<Optional<BlockSummary>, BlockSummary.Response> getBlockSummaryByEpoch(Epoch epoch) {
		return new Request<>(this.service, "cfx_getBlockByEpochNumber", BlockSummary.Response.class, epoch.getValue(), false)
				.withRetry(this.retry, this.intervalMillis);
	}
	
	@Override
	public Request<Optional<Block>, Block.Response> getBlockByEpoch(Epoch epoch) {
		return new Request<>(this.service, "cfx_getBlockByEpochNumber", Block.Response.class, epoch.getValue(), true)
				.withRetry(this.retry, this.intervalMillis);
	}

	@Override
	public Request<String, StringResponse> getBestBlockHash() {
		return new Request<>(this.service, "cfx_getBestBlockHash", StringResponse.class)
				.withRetry(this.retry, this.intervalMillis);
	}

	@Override
	public Request<BigInteger, BigIntResponse> getNonce(String address, Epoch... epoch) {
		if (epoch.length == 0) {
			return new Request<>(this.service, "cfx_getNextNonce", BigIntResponse.class, address)
					.withRetry(this.retry, this.intervalMillis);
		} else {
			return new Request<>(this.service, "cfx_getNextNonce", BigIntResponse.class, address, epoch[0].getValue())
					.withRetry(this.retry, this.intervalMillis);
		}
	}

	@Override
	public Request<String, StringResponse> sendRawTransaction(String hexEncoded) {
		return new Request<>(this.service, "cfx_sendRawTransaction", StringResponse.class, hexEncoded)
				.withRetry(this.retry, this.intervalMillis);
	}

	@Override
	public Request<String, StringResponse> call(Call request, Epoch... epoch) {
		if (epoch.length == 0) {
			return new Request<>(this.service, "cfx_call", StringResponse.class, request)
					.withRetry(this.retry, this.intervalMillis);
		} else {
			return new Request<>(this.service, "cfx_call", StringResponse.class, request, epoch[0].getValue())
					.withRetry(this.retry, this.intervalMillis);
		}
	}

	@Override
	public Request<List<Log>, Log.Response> getLogs(LogFilter filter) {
		return new Request<>(this.service, "cfx_getLogs", Log.Response.class, filter)
				.withRetry(this.retry, this.intervalMillis);
	}

	@Override
	public Request<Optional<Transaction>, Transaction.Response> getTransactionByHash(String txHash) {
		return new Request<>(this.service, "cfx_getTransactionByHash", Transaction.Response.class, txHash)
				.withRetry(this.retry, this.intervalMillis);
	}

	@Override
	public Request<UsedGasAndCollateral, UsedGasAndCollateral.Response> estimateGasAndCollateral(Call request, Epoch... epoch) {
		if (epoch.length == 0) {
			return new Request<>(this.service, "cfx_estimateGasAndCollateral", UsedGasAndCollateral.Response.class, request)
					.withRetry(this.retry, this.intervalMillis);
		} else {
			return new Request<>(this.service, "cfx_estimateGasAndCollateral", UsedGasAndCollateral.Response.class, request, epoch[0].getValue())
					.withRetry(this.retry, this.intervalMillis);
		}
	}

	@Override
	public Request<List<String>, Block.ListResponse> getBlocksByEpoch(Epoch epoch) {
		return new Request<>(this.service, "cfx_getBlocksByEpoch", Block.ListResponse.class, epoch.getValue())
				.withRetry(this.retry, this.intervalMillis);
	}

	@Override
	public Request<Optional<Receipt>, Receipt.Response> getTransactionReceipt(String txHash) {
		return new Request<>(this.service, "cfx_getTransactionReceipt", Receipt.Response.class, txHash)
				.withRetry(this.retry, this.intervalMillis);
	}
}

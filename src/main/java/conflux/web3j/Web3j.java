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
import conflux.web3j.response.BlockResponse;
import conflux.web3j.response.BlockSummary;
import conflux.web3j.response.BlockSummaryResponse;
import conflux.web3j.response.BlocksResponse;
import conflux.web3j.response.Log;
import conflux.web3j.response.LogsResponse;
import conflux.web3j.response.Receipt;
import conflux.web3j.response.ReceiptResponse;
import conflux.web3j.response.StringResponse;
import conflux.web3j.response.Transaction;
import conflux.web3j.response.TransactionResponse;
import conflux.web3j.response.UsedGasAndCollateral;
import conflux.web3j.response.UsedGasAndCollateralResponse;

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
	public Request<Optional<BlockSummary>, BlockSummaryResponse> getBlockSummaryByHash(String blockHash) {
		return new Request<>(this.service, "cfx_getBlockByHash", BlockSummaryResponse.class, blockHash, false)
				.withRetry(this.retry, this.intervalMillis);
	}

	@Override
	public Request<Optional<Block>, BlockResponse> getBlockByHash(String blockHash) {
		return new Request<>(this.service, "cfx_getBlockByHash", BlockResponse.class, blockHash, true)
				.withRetry(this.retry, this.intervalMillis);
	}

	@Override
	public Request<Optional<BlockSummary>, BlockSummaryResponse> getBlockSummaryByEpoch(Epoch epoch) {
		return new Request<>(this.service, "cfx_getBlockByEpochNumber", BlockSummaryResponse.class, epoch.getValue(), false)
				.withRetry(this.retry, this.intervalMillis);
	}
	
	@Override
	public Request<Optional<Block>, BlockResponse> getBlockByEpoch(Epoch epoch) {
		return new Request<>(this.service, "cfx_getBlockByEpochNumber", BlockResponse.class, epoch.getValue(), true)
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
	public Request<List<Log>, LogsResponse> getLogs(LogFilter filter) {
		return new Request<>(this.service, "cfx_getLogs", LogsResponse.class, filter)
				.withRetry(this.retry, this.intervalMillis);
	}

	@Override
	public Request<Optional<Transaction>, TransactionResponse> getTransactionByHash(String txHash) {
		return new Request<>(this.service, "cfx_getTransactionByHash", TransactionResponse.class, txHash)
				.withRetry(this.retry, this.intervalMillis);
	}

	@Override
	public Request<UsedGasAndCollateral, UsedGasAndCollateralResponse> estimateGasAndCollateral(Call request, Epoch... epoch) {
		if (epoch.length == 0) {
			return new Request<>(this.service, "cfx_estimateGasAndCollateral", UsedGasAndCollateralResponse.class, request)
					.withRetry(this.retry, this.intervalMillis);
		} else {
			return new Request<>(this.service, "cfx_estimateGasAndCollateral", UsedGasAndCollateralResponse.class, request, epoch[0].getValue())
					.withRetry(this.retry, this.intervalMillis);
		}
	}

	@Override
	public Request<List<String>, BlocksResponse> getBlocksByEpoch(Epoch epoch) {
		return new Request<>(this.service, "cfx_getBlocksByEpoch", BlocksResponse.class, epoch.getValue())
				.withRetry(this.retry, this.intervalMillis);
	}

	@Override
	public Request<Optional<Receipt>, ReceiptResponse> getTransactionReceipt(String txHash) {
		return new Request<>(this.service, "cfx_getTransactionReceipt", ReceiptResponse.class, txHash)
				.withRetry(this.retry, this.intervalMillis);
	}
}

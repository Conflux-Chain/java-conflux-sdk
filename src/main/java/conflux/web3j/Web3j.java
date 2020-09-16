package conflux.web3j;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import conflux.web3j.response.*;
import conflux.web3j.response.events.EpochNotification;
import conflux.web3j.response.events.LogNotification;
import conflux.web3j.response.events.NewHeadsNotification;
import io.reactivex.Flowable;
import org.web3j.protocol.Web3jService;

import conflux.web3j.request.Call;
import conflux.web3j.request.Epoch;
import conflux.web3j.request.LogFilter;
import org.web3j.protocol.core.Response;


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
	public Request<Optional<String>, StringNullableResponse> getAdmin(String address, Epoch... epoch) {
		if (epoch.length == 0) {
			return new Request<>(this.service, "cfx_getAdmin", StringNullableResponse.class, address)
					.withRetry(this.retry, this.intervalMillis);
		} else {
			return new Request<>(this.service, "cfx_getAdmin", StringNullableResponse.class, address, epoch[0].getValue())
					.withRetry(this.retry, this.intervalMillis);
		}
	}
	
	@Override
	public Request<SponsorInfo, SponsorInfo.Response> getSponsorInfo(String address, Epoch... epoch) {
		if (epoch.length == 0) {
			return new Request<>(this.service, "cfx_getSponsorInfo", SponsorInfo.Response.class, address)
					.withRetry(this.retry, this.intervalMillis);
		} else {
			return new Request<>(this.service, "cfx_getSponsorInfo", SponsorInfo.Response.class, address, epoch[0].getValue())
					.withRetry(this.retry, this.intervalMillis);
		}
	}
	
	@Override
	public Request<BigInteger, BigIntResponse> getStakingBalance(String address, Epoch... epoch) {
		if (epoch.length == 0) {
			return new Request<>(this.service, "cfx_getStakingBalance", BigIntResponse.class, address)
					.withRetry(this.retry, this.intervalMillis);
		} else {
			return new Request<>(this.service, "cfx_getStakingBalance", BigIntResponse.class, address, epoch[0].getValue())
					.withRetry(this.retry, this.intervalMillis);
		}
	}
	
	@Override
	public Request<BigInteger, BigIntResponse> getCollateralForStorage(String address, Epoch... epoch) {
		if (epoch.length == 0) {
			return new Request<>(this.service, "cfx_getCollateralForStorage", BigIntResponse.class, address)
					.withRetry(this.retry, this.intervalMillis);
		} else {
			return new Request<>(this.service, "cfx_getCollateralForStorage", BigIntResponse.class, address, epoch[0].getValue())
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
	public Request<Optional<String>, StringNullableResponse> getStorageAt(String address, String pos, Epoch... epoch) {
		if (epoch.length == 0) {
			return new Request<>(this.service, "cfx_getStorageAt", StringNullableResponse.class, address, pos)
					.withRetry(this.retry, this.intervalMillis);
		} else {
			return new Request<>(this.service, "cfx_getStorageAt", StringNullableResponse.class, address, pos, epoch[0].getValue())
					.withRetry(this.retry, this.intervalMillis);
		}
	}
	
	@Override
	public Request<Optional<StorageRoot>, StorageRoot.Response> getStorageRoot(String address, Epoch... epoch) {
		if (epoch.length == 0) {
			return new Request<>(this.service, "cfx_getStorageRoot", StorageRoot.Response.class, address)
					.withRetry(this.retry, this.intervalMillis);
		} else {
			return new Request<>(this.service, "cfx_getStorageRoot", StorageRoot.Response.class, address, epoch[0].getValue())
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
	public Request<List<String>, Block.ListResponse> getSkippedBlocksByEpoch(Epoch epoch) {
		return new Request<>(this.service, "cfx_getSkippedBlocksByEpoch", Block.ListResponse.class, epoch.getValue())
				.withRetry(this.retry, this.intervalMillis);
	}

	@Override
	public Request<Optional<Receipt>, Receipt.Response> getTransactionReceipt(String txHash) {
		return new Request<>(this.service, "cfx_getTransactionReceipt", Receipt.Response.class, txHash)
				.withRetry(this.retry, this.intervalMillis);
	}
	
	@Override
	public Request<AccountInfo, AccountInfo.Response> getAccount(String address, Epoch... epoch) {
		if (epoch.length == 0) {
			return new Request<>(this.service, "cfx_getAccount", AccountInfo.Response.class, address)
					.withRetry(this.retry, this.intervalMillis);
		} else {
			return new Request<>(this.service, "cfx_getAccount", AccountInfo.Response.class, address, epoch[0].getValue())
					.withRetry(this.retry, this.intervalMillis);
		}
	}
	
	@Override
	public Request<BigInteger, BigIntResponse> getInterestRate(Epoch... epoch) {
		if (epoch.length == 0) {
			return new Request<>(this.service, "cfx_getInterestRate", BigIntResponse.class)
					.withRetry(this.retry, this.intervalMillis);
		} else {
			return new Request<>(this.service, "cfx_getInterestRate", BigIntResponse.class, epoch[0].getValue())
					.withRetry(this.retry, this.intervalMillis);
		}
	}
	
	@Override
	public Request<BigInteger, BigIntResponse> getAccumulateInterestRate(Epoch... epoch) {
		if (epoch.length == 0) {
			return new Request<>(this.service, "cfx_getAccumulateInterestRate", BigIntResponse.class)
					.withRetry(this.retry, this.intervalMillis);
		} else {
			return new Request<>(this.service, "cfx_getAccumulateInterestRate", BigIntResponse.class, epoch[0].getValue())
					.withRetry(this.retry, this.intervalMillis);
		}
	}
	
	@Override
	public Request<Optional<BigInteger>, BigIntNullableResponse> getConfirmationRisk(String blockHash) {
		return new Request<>(this.service, "cfx_getConfirmationRiskByHash", BigIntNullableResponse.class, blockHash)
				.withRetry(this.retry, this.intervalMillis);
	}
	
	@Override
	public Request<BigDecimal, BlockRevertRateResponse> getBlockRevertRate(String blockHash) {
		return new Request<>(this.service, "cfx_getConfirmationRiskByHash", BlockRevertRateResponse.class, blockHash)
				.withRetry(this.retry, this.intervalMillis);
	}
	
	@Override
	public Request<Status, Status.Response> getStatus() {
		return new Request<>(this.service, "cfx_getStatus", Status.Response.class)
				.withRetry(this.retry, this.intervalMillis);
	}
	
	@Override
	public Request<List<RewardInfo>, RewardInfo.Response> getReward(Epoch epoch) {
		return new Request<>(this.service, "cfx_getBlockRewardInfo", RewardInfo.Response.class, epoch.getValue())
				.withRetry(this.retry, this.intervalMillis);
	}
	
	@Override
	public Request<String, StringResponse> getClientVersion() {
		return new Request<>(this.service, "cfx_clientVersion", StringResponse.class)
				.withRetry(this.retry, this.intervalMillis);
	}

	@Override
	public <T,R extends Response<?> & HasValue<T>> Request<T, R> getCustomizedRequest(Class<R> responseType, String method, Object... params){
		return new Request<>(this.service, method, responseType, params)
				.withRetry(this.retry, this.intervalMillis);
	}

	@Override
	public Flowable<NewHeadsNotification> subscribeNewHeads() {
		return service.subscribe(
				new org.web3j.protocol.core.Request<>(
						"cfx_subscribe",
						Collections.singletonList("newHeads"),
						service,
						Subscribe.class),
				"cfx_unsubscribe",
				NewHeadsNotification.class);
	}

	@Override
	public Flowable<LogNotification> subscribeLogs(LogFilter filter) {
		return service.subscribe(
				new org.web3j.protocol.core.Request<>(
						"cfx_subscribe",
						Arrays.asList("logs", filter),
						service,
						Subscribe.class),
				"cfx_unsubscribe",
				LogNotification.class);
	}

	@Override
	public Flowable<EpochNotification> subscribeEpochs() {
		return service.subscribe(
				new org.web3j.protocol.core.Request<>(
						"cfx_subscribe",
						Collections.singletonList("epochs"),
						service,
						Subscribe.class),
				"cfx_unsubscribe",
				EpochNotification.class);
	}
}

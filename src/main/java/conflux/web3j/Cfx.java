package conflux.web3j;

import java.io.Closeable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import conflux.web3j.response.*;
import conflux.web3j.types.Address;
import org.web3j.protocol.Web3jService;
import org.web3j.protocol.core.Response;
import org.web3j.protocol.http.HttpService;

import conflux.web3j.request.Call;
import conflux.web3j.request.Epoch;
import conflux.web3j.request.LogFilter;
import conflux.web3j.types.SendTransactionResult;

/** Core Conflux JSON-RPC API. */
public interface Cfx extends Closeable, CfxPubSub {
	
	static Cfx create(String url) {
		return new Web3j(new HttpService(url));
	}

	static Cfx create(String url, int retry) {
		return new Web3j(new HttpService(url), retry, 0);
	}
	
	static Cfx create(String url, int retry, long intervalMillis) {
		return new Web3j(new HttpService(url), retry, intervalMillis);
	}

	static Cfx create(Web3jService service) {
		return new Web3j(service);
	}

	static Cfx create(Web3jService service, int retry, long intervalMillis) {
		return new Web3j(service, retry, intervalMillis);
	}

	BigInteger getNetworkId();

	int getIntNetworkId();

	BigInteger getChainId();
	
	Request<BigInteger, BigIntResponse> getGasPrice();
	
	Request<BigInteger, BigIntResponse> getEpochNumber(Epoch... epoch);
	
	Request<BigInteger, BigIntResponse> getBalance(Address address, Epoch... epoch);
	
	Request<Optional<String>, StringNullableResponse> getAdmin(Address address, Epoch... epoch);
	
	Request<SponsorInfo, SponsorInfo.Response> getSponsorInfo(Address address, Epoch... epoch);
	
	Request<BigInteger, BigIntResponse> getStakingBalance(Address address, Epoch... epoch);
	
	Request<BigInteger, BigIntResponse> getCollateralForStorage(Address address, Epoch... epoch);
	
	Request<String, StringResponse> getCode(Address address, Epoch... epoch);
	
	Request<Optional<String>, StringNullableResponse> getStorageAt(Address address, String pos, Epoch... epoch);
	
	Request<Optional<StorageRoot>, StorageRoot.Response> getStorageRoot(Address address, Epoch... epoch);
	
	Request<Optional<BlockSummary>, BlockSummary.Response> getBlockSummaryByHash(String blockHash);
	
	Request<Optional<Block>, Block.Response> getBlockByHash(String blockHash);
	
	Request<Optional<BlockSummary>, BlockSummary.Response> getBlockSummaryByEpoch(Epoch epoch);
	
	Request<Optional<Block>, Block.Response> getBlockByEpoch(Epoch epoch);
	
	Request<String, StringResponse> getBestBlockHash();
	
	Request<BigInteger, BigIntResponse> getNonce(Address address, Epoch... epoch);

	Request<BigInteger, BigIntResponse> txpoolNextNonce(Address address);

	Request<String, StringResponse> sendRawTransaction(String hexEncoded);
	
	Request<String, StringResponse> call(Call request, Epoch... epoch);
	
	Request<List<Log>, Log.Response> getLogs(LogFilter filter);
	
	Request<Optional<Transaction>, Transaction.Response> getTransactionByHash(String txHash);
	
	Request<UsedGasAndCollateral, UsedGasAndCollateral.Response> estimateGasAndCollateral(Call request, Epoch... epoch);
	
	Request<List<String>, Block.ListResponse> getBlocksByEpoch(Epoch epoch);
	
	Request<List<String>, Block.ListResponse> getSkippedBlocksByEpoch(Epoch epoch);
	
	Request<Optional<Receipt>, Receipt.Response> getTransactionReceipt(String txHash);
	
	Request<AccountInfo, AccountInfo.Response> getAccount(Address address, Epoch... epoch);
	
	Request<BigInteger, BigIntResponse> getInterestRate(Epoch... epoch);
	
	Request<BigInteger, BigIntResponse> getAccumulateInterestRate(Epoch... epoch);
	
	Request<Optional<BigInteger>, BigIntNullableResponse> getConfirmationRisk(String blockHash);

	Request<BigDecimal, BlockRevertRateResponse> getBlockRevertRate(String blockHash);
	
	Request<Status, Status.Response> getStatus();
	
	Request<List<RewardInfo>, RewardInfo.Response> getReward(Epoch epoch);
	
	Request<String, StringResponse> getClientVersion();

	Request<List<DepositInfo>, DepositInfo.ListResponse> getDepositList(Address address, Epoch... epoch);

	Request<List<VoteStakeInfo>, VoteStakeInfo.ListResponse> getVoteList(Address address, Epoch... epoch);

	Request<SupplyInfo, SupplyInfo.Response> getSupplyInfo(Epoch... epoch);

	Request<Optional<AccountPendingInfo>, AccountPendingInfo.Response> getAccountPendingInfo(Address address);

	Request<AccountPendingTransactions, AccountPendingTransactions.Response> getAccountPendingTransactions(Address address);

	Request<List<String>, StringListResponse.Response> rpcModules();

	Request<PoSEconomics, PoSEconomics.Response> getPoSEconomics();

	Request<PoSEpochRewards, PoSEpochRewards.Response> getPoSRewardByEpoch(Address address, Epoch epoch);

	<T,R extends Response<?> & HasValue<T>> Request<T, R> getCustomizedRequest(Class<R> responseType, String method, Object... params);
	
	default Receipt waitForReceipt(String txHash) throws InterruptedException {
		return this.waitForReceipt(txHash, 1000);
	}
	
	default Receipt waitForReceipt(String txHash, long intervalMillis) throws InterruptedException {
		Optional<Receipt> receipt = Optional.empty();
		
		while (!receipt.isPresent()) {
			Thread.sleep(intervalMillis);
			receipt = this.getTransactionReceipt(txHash).sendAndGet();
		}
		
		return receipt.get();
	}
	
	default void waitForNonce(Address address, BigInteger nonceUntil) throws InterruptedException {
		this.waitForNonce(address, nonceUntil, 1000);
	}
	
	default void waitForNonce(Address address, BigInteger nonceUntil, long intervalMillis) throws InterruptedException {
		while (this.getNonce(address).sendAndGet().compareTo(nonceUntil) < 0) {
			Thread.sleep(intervalMillis);
		}
	}
	
	default SendTransactionResult sendRawTransactionAndGet(String hexEncoded) throws RpcException {
		StringResponse response = this.sendRawTransaction(hexEncoded).sendWithRetry();
		
		return response.getError() == null
				? new SendTransactionResult(response.getValue())
				: new SendTransactionResult(response.getError());
	}
	
}



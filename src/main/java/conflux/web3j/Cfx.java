package conflux.web3j;

import java.io.Closeable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.web3j.protocol.http.HttpService;

import conflux.web3j.request.Call;
import conflux.web3j.request.Epoch;
import conflux.web3j.request.LogFilter;
import conflux.web3j.response.AccountInfo;
import conflux.web3j.response.BigIntNullableResponse;
import conflux.web3j.response.BigIntResponse;
import conflux.web3j.response.Block;
import conflux.web3j.response.BlockRevertRateResponse;
import conflux.web3j.response.BlockSummary;
import conflux.web3j.response.Log;
import conflux.web3j.response.Receipt;
import conflux.web3j.response.RewardInfo;
import conflux.web3j.response.SponsorInfo;
import conflux.web3j.response.Status;
import conflux.web3j.response.StorageRoot;
import conflux.web3j.response.StringNullableResponse;
import conflux.web3j.response.StringResponse;
import conflux.web3j.response.Transaction;
import conflux.web3j.response.UsedGasAndCollateral;
import conflux.web3j.types.SendTransactionResult;

/** Core Conflux JSON-RPC API. */
public interface Cfx extends Closeable {
	
	static Cfx create(String url) {
		return new Web3j(new HttpService(url));
	}
	
	static Cfx create(String url, int retry) {
		return new Web3j(new HttpService(url), retry, 0);
	}
	
	static Cfx create(String url, int retry, long intervalMillis) {
		return new Web3j(new HttpService(url), retry, intervalMillis);
	}
	
	Request<BigInteger, BigIntResponse> getGasPrice();
	
	Request<BigInteger, BigIntResponse> getEpochNumber(Epoch... epoch);
	
	Request<BigInteger, BigIntResponse> getBalance(String address, Epoch... epoch);
	
	Request<Optional<String>, StringNullableResponse> getAdmin(String address, Epoch... epoch);
	
	Request<SponsorInfo, SponsorInfo.Response> getSponsorInfo(String address, Epoch... epoch);
	
	Request<BigInteger, BigIntResponse> getStakingBalance(String address, Epoch... epoch);
	
	Request<BigInteger, BigIntResponse> getCollateralForStorage(String address, Epoch... epoch);
	
	Request<String, StringResponse> getCode(String address, Epoch... epoch);
	
	Request<Optional<String>, StringNullableResponse> getStorageAt(String address, String pos, Epoch... epoch);
	
	Request<Optional<StorageRoot>, StorageRoot.Response> getStorageRoot(String address, Epoch... epoch);
	
	Request<Optional<BlockSummary>, BlockSummary.Response> getBlockSummaryByHash(String blockHash);
	
	Request<Optional<Block>, Block.Response> getBlockByHash(String blockHash);
	
	Request<Optional<BlockSummary>, BlockSummary.Response> getBlockSummaryByEpoch(Epoch epoch);
	
	Request<Optional<Block>, Block.Response> getBlockByEpoch(Epoch epoch);
	
	Request<String, StringResponse> getBestBlockHash();
	
	Request<BigInteger, BigIntResponse> getNonce(String address, Epoch... epoch);
	
	Request<String, StringResponse> sendRawTransaction(String hexEncoded);
	
	Request<String, StringResponse> call(Call request, Epoch... epoch);
	
	Request<List<Log>, Log.Response> getLogs(LogFilter filter);
	
	Request<Optional<Transaction>, Transaction.Response> getTransactionByHash(String txHash);
	
	Request<UsedGasAndCollateral, UsedGasAndCollateral.Response> estimateGasAndCollateral(Call request, Epoch... epoch);
	
	Request<List<String>, Block.ListResponse> getBlocksByEpoch(Epoch epoch);
	
	Request<List<String>, Block.ListResponse> getSkippedBlocksByEpoch(Epoch epoch);
	
	Request<Optional<Receipt>, Receipt.Response> getTransactionReceipt(String txHash);
	
	Request<AccountInfo, AccountInfo.Response> getAccount(String address, Epoch... epoch);
	
	Request<BigInteger, BigIntResponse> getInterestRate(Epoch... epoch);
	
	Request<BigInteger, BigIntResponse> getAccumulateInterestRate(Epoch... epoch);
	
	Request<Optional<BigInteger>, BigIntNullableResponse> getConfirmationRisk(String blockHash);
	Request<BigDecimal, BlockRevertRateResponse> getBlockRevertRate(String blockHash);
	
	Request<Status, Status.Response> getStatus();
	
	Request<List<RewardInfo>, RewardInfo.Response> getReward(Epoch epoch);
	
	Request<String, StringResponse> getClientVersion();
	
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
	
	default void waitForNonce(String address, BigInteger nonceUntil) throws InterruptedException {
		this.waitForNonce(address, nonceUntil, 1000);
	}
	
	default void waitForNonce(String address, BigInteger nonceUntil, long intervalMillis) throws InterruptedException {
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

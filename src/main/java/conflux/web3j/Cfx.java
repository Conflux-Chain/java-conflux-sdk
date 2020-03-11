package conflux.web3j;

import java.io.Closeable;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.web3j.protocol.http.HttpService;

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
	
	Request<String, StringResponse> getCode(String address, Epoch... epoch);
	
	Request<Optional<BlockSummary>, BlockSummaryResponse> getBlockSummaryByHash(String blockHash);
	
	Request<Optional<Block>, BlockResponse> getBlockByHash(String blockHash);
	
	Request<Optional<BlockSummary>, BlockSummaryResponse> getBlockSummaryByEpoch(Epoch epoch);
	
	Request<Optional<Block>, BlockResponse> getBlockByEpoch(Epoch epoch);
	
	Request<String, StringResponse> getBestBlockHash();
	
	Request<BigInteger, BigIntResponse> getTransactionCount(String address, Epoch... epoch);
	
	Request<String, StringResponse> sendRawTransaction(String hexEncoded);
	
	Request<String, StringResponse> call(Call request, Epoch... epoch);
	
	Request<List<Log>, LogsResponse> getLogs(LogFilter filter);
	
	Request<Optional<Transaction>, TransactionResponse> getTransactionByHash(String txHash);
	
	Request<BigInteger, BigIntResponse> estimateGas(Call request, Epoch... epoch);
	
	Request<List<String>, BlocksResponse> getBlocksByEpoch(Epoch epoch);
	
	Request<Optional<Receipt>, ReceiptResponse> getTransactionReceipt(String txHash);
	
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
		while (this.getTransactionCount(address).sendAndGet().compareTo(nonceUntil) < 0) {
			Thread.sleep(intervalMillis);
		}
	}
	
}

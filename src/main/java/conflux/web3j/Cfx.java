package conflux.web3j;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

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
public interface Cfx {
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
}

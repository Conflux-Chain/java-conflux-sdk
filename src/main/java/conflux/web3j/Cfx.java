package conflux.web3j;

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

/** Core Conflux JSON-RPC API. */
public interface Cfx {
	Request<?, BigIntResponse> getGasPrice();
	
	Request<?, BigIntResponse> getEpochNumber(Epoch... epoch);
	
	Request<?, BigIntResponse> getBalance(String address, Epoch... epoch);
	
	Request<?, StringResponse> getCode(String address, Epoch... epoch);
	
	Request<?, BlockSummaryResponse> getBlockSummaryByHash(String blockHash);
	
	Request<?, BlockResponse> getBlockByHash(String blockHash);
	
	Request<?, BlockSummaryResponse> getBlockSummaryByEpoch(Epoch epoch);
	
	Request<?, BlockResponse> getBlockByEpoch(Epoch epoch);
	
	Request<?, StringResponse> getBestBlockHash();
	
	Request<?, BigIntResponse> getTransactionCount(String address, Epoch... epoch);
	
	Request<?, StringResponse> sendRawTransaction(String hexEncoded);
	
	Request<?, StringResponse> call(Call request, Epoch... epoch);
	
	Request<?, LogsResponse> getLogs(LogFilter filter);
	
	Request<?, TransactionResponse> getTransactionByHash(String txHash);
	
	Request<?, BigIntResponse> estimateGas(Call request, Epoch... epoch);
	
	Request<?, BlocksResponse> getBlocksByEpoch(Epoch epoch);
	
	Request<?, ReceiptResponse> getTransactionReceipt(String txHash);
}

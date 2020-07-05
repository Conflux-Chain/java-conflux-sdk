package conflux.web3j.contract.diagnostics;

import java.math.BigInteger;
import java.util.Optional;

import conflux.web3j.Cfx;
import conflux.web3j.RpcException;
import conflux.web3j.contract.abi.DecodeUtil;
import conflux.web3j.request.Call;
import conflux.web3j.request.Epoch;
import conflux.web3j.response.Receipt;
import conflux.web3j.response.StringResponse;
import conflux.web3j.response.Transaction;
import conflux.web3j.response.UsedGasAndCollateral;

public class Recall {
	
	private Cfx cfx;
	
	public Recall(Cfx cfx) {
		this.cfx = cfx;
	}
	
	public String diagnoseFailure(String txHash) throws RpcException {
		Optional<Transaction> tx = this.cfx.getTransactionByHash(txHash).sendAndGet();
		if (!tx.isPresent()) {
			return "tx not found";
		}
		
		Optional<Receipt> receipt = this.cfx.getTransactionReceipt(txHash).sendAndGet();
		if (!receipt.isPresent()) {
			return "receipt not found";
		}
		
		if (receipt.get().getOutcomeStatus() == 0) {
			return "tx not failed";
		}
		
		Call call = this.convert(tx.get());
		
		return this.execute(tx.get(), receipt.get(), call);
	}
	
	private Call convert(Transaction tx) {
		Call call = new Call();
		
		call.setFrom(tx.getFrom());
		call.setNonce(tx.getNonce());
		
		if (tx.getTo().isPresent()) {
			call.setTo(tx.getTo().get());
		}
		
		call.setValue(tx.getValue());
		call.setGasPrice(tx.getGasPrice());
		call.setGas(tx.getGas());
		call.setData(tx.getData());
		call.setStorageLimit(tx.getStorageLimit());
		
		return call;
	}
	
	private String execute(Transaction tx, Receipt receipt, Call call) {
		BigInteger execEpoch = receipt.getEpochNumber().subtract(BigInteger.ONE);
		
		StringResponse response = this.cfx.call(call, Epoch.numberOf(execEpoch)).sendWithRetry();
		
		if (response.getError() == null) {
			return "Execution succeeded: " + response.getValue();
		} else {
			return DecodeUtil.decodeErrorData(response.getError().getData());
		}
	}
	
	public UsedGasAndCollateral estimate(String txHash) throws RpcException {
		Transaction tx = this.cfx.getTransactionByHash(txHash).sendAndGet().get();
		Receipt receipt = this.cfx.getTransactionReceipt(txHash).sendAndGet().get();
		
		Call call = this.convert(tx);
		call.setGas(null);
		call.setStorageLimit(null);
		
		BigInteger execEpoch = receipt.getEpochNumber().subtract(BigInteger.ONE);
		return this.cfx.estimateGasAndCollateral(call, Epoch.numberOf(execEpoch)).sendAndGet();
	}
	
	public void diagnoseEstimation(String txHash) throws RpcException {
		Transaction tx = this.cfx.getTransactionByHash(txHash).sendAndGet().get();
		Receipt receipt = this.cfx.getTransactionReceipt(txHash).sendAndGet().get();
		
		Call call = this.convert(tx);
		call.setGas(null);
		call.setStorageLimit(null);
		
		BigInteger execEpoch = receipt.getEpochNumber().subtract(BigInteger.ONE);
		UsedGasAndCollateral estimation = this.cfx.estimateGasAndCollateral(call, Epoch.numberOf(execEpoch)).sendAndGet();
		
		System.out.printf("Transaction: gas = %s, storage = %s\n", tx.getGas(), tx.getStorageLimit());
		System.out.printf("Estimation : gas = %s, storage = %s\n", estimation.getGasUsed(), estimation.getStorageCollateralized());
		
		if (tx.getGas().compareTo(estimation.getGasUsed()) < 0) {
			System.err.println("ERROR: gas not enough");
		}
		
		if (tx.getStorageLimit().compareTo(estimation.getStorageCollateralized()) < 0) {
			System.err.println("ERROR: storage not enough");
		}
	}

}

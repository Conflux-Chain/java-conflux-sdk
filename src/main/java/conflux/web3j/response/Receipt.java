package conflux.web3j.response;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import conflux.web3j.types.Address;
import org.web3j.utils.Numeric;

public class Receipt {
	
	public static class Response extends CfxNullableResponse<Receipt> {}
	
	private String transactionHash;
	private String index;
	private String blockHash;
	private String epochNumber;
	private Address from;
	private Address to;
	private String gasUsed;
	private String contractCreated;
	private List<Log> logs;
	private String logsBloom;
	private String stateRoot;
	private String outcomeStatus;
	private String txExecErrorMsg;
	private boolean gasCoveredBySponsor;
	private boolean storageCoveredBySponsor;
	private String storageCollateralized;
	private List<StorageChange> storageReleased;

	
	public String getTransactionHash() {
		return transactionHash;
	}
	
	public void setTransactionHash(String transactionHash) {
		this.transactionHash = transactionHash;
	}
	
	public BigInteger getIndex() {
		return Numeric.decodeQuantity(this.index);
	}
	
	public void setIndex(String index) {
		this.index = index;
	}
	
	public String getBlockHash() {
		return blockHash;
	}
	
	public void setBlockHash(String blockHash) {
		this.blockHash = blockHash;
	}
	
	public BigInteger getEpochNumber() {
		return Numeric.decodeQuantity(this.epochNumber);
	}
	
	public void setEpochNumber(String epochNumber) {
		this.epochNumber = epochNumber;
	}
	
	public Address getFrom() {
		return from;
	}
	
	public void setFrom(Address from) {
		this.from = from;
	}
	
	public Optional<Address> getTo() {
		if (this.to == null) {
			return Optional.empty();
		} else {
			return Optional.of(this.to);
		}
	}
	
	public void setTo(Address to) {
		this.to = to;
	}
	
	public BigInteger getGasUsed() {
		return Numeric.decodeQuantity(this.gasUsed);
	}
	
	public void setGasUsed(String gasUsed) {
		this.gasUsed = gasUsed;
	}
	
	public Optional<String> getContractCreated() {
		if (this.contractCreated == null || this.contractCreated.isEmpty()) {
			return Optional.empty();
		} else {
			return Optional.of(this.contractCreated);
		}
	}
	
	public void setContractCreated(String contractCreated) {
		this.contractCreated = contractCreated;
	}
	
	public List<Log> getLogs() {
		return logs;
	}
	
	public void setLogs(List<Log> logs) {
		this.logs = logs;
	}
	
	public String getLogsBloom() {
		return logsBloom;
	}
	
	public void setLogsBloom(String logsBloom) {
		this.logsBloom = logsBloom;
	}
	
	public String getStateRoot() {
		return stateRoot;
	}
	
	public void setStateRoot(String stateRoot) {
		this.stateRoot = stateRoot;
	}
	
	public short getOutcomeStatus() {
		return Numeric.decodeQuantity(this.outcomeStatus).shortValueExact();
	}
	
	public void setOutcomeStatus(String outcomeStatus) {
		this.outcomeStatus = outcomeStatus;
	}

	public String getTxExecErrorMsg() {
		return this.txExecErrorMsg;
	}

	public void setTxExecErrorMsg(String errorMsg) {this.txExecErrorMsg = errorMsg;}

	public boolean getGasCoveredBySponsor() {
		return gasCoveredBySponsor;
	}

	public void setGasCoveredBySponsor(boolean gasCovered) {
		this.gasCoveredBySponsor = gasCovered;
	}

	public boolean getStorageCoveredBySponsor() {
		return storageCoveredBySponsor;
	}

	public void setStorageCoveredBySponsor(boolean storageCovered) {
		this.storageCoveredBySponsor = storageCovered;
	}

	public BigInteger getStorageCollateralized() {
		return Numeric.decodeQuantity(storageCollateralized);
	}

	public void setStorageCollateralized(String collateralized) {
		this.storageCollateralized = collateralized;
	}

	public List<StorageChange> getStorageReleased() {
		return this.storageReleased;
	}

	public void setStorageReleased(List<StorageChange> change) {
		this.storageReleased = change;
	}
}

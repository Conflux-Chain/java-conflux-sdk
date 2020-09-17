package conflux.web3j.response;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.web3j.utils.Numeric;

public class Log {
	
	public static class Response extends CfxListResponse<Log> {}
	
	private String address;
	private List<String> topics;
	private String data;
	private String blockHash;
	private String epochNumber;
	private String transactionHash;
	private String transactionIndex;
	private String logIndex;
	private String transactionLogIndex;
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<String> getTopics() {
		return topics;
	}

	public void setTopics(List<String> topics) {
		this.topics = topics;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
	public Optional<String> getBlockHash() {
		if (this.blockHash == null || this.blockHash.isEmpty()) {
			return Optional.empty();
		} else {
			return Optional.of(this.blockHash);
		}
	}
	
	public void setBlockHash(String blockHash) {
		this.blockHash = blockHash;
	}
	
	public Optional<BigInteger> getEpochNumber() {
		if (this.epochNumber == null || this.epochNumber.isEmpty()) {
			return Optional.empty();
		} else {
			return Optional.of(Numeric.decodeQuantity(this.epochNumber));
		}
	}
	
	public void setEpochNumber(String epochNumber) {
		this.epochNumber = epochNumber;
	}
	
	public Optional<String> getTransactionHash() {
		if (this.transactionHash == null || this.transactionHash.isEmpty()) {
			return Optional.empty();
		} else {
			return Optional.of(this.transactionHash);
		}
	}
	
	public void setTransactionHash(String transactionHash) {
		this.transactionHash = transactionHash;
	}
	
	public Optional<BigInteger> getTransactionIndex() {
		if (this.transactionIndex == null || this.transactionIndex.isEmpty()) {
			return Optional.empty();
		} else {
			return Optional.of(Numeric.decodeQuantity(this.transactionIndex));
		}
	}
	
	public void setTransactionIndex(String transactionIndex) {
		this.transactionIndex = transactionIndex;
	}
	
	public Optional<BigInteger> getLogIndex() {
		if (this.logIndex == null || this.logIndex.isEmpty()) {
			return Optional.empty();
		} else {
			return Optional.of(Numeric.decodeQuantity(this.logIndex));
		}
	}
	
	public void setLogIndex(String logIndex) {
		this.logIndex = logIndex;
	}
	
	public Optional<BigInteger> getTransactionLogIndex() {
		if (this.transactionLogIndex == null || this.transactionLogIndex.isEmpty()) {
			return Optional.empty();
		} else {
			return Optional.of(Numeric.decodeQuantity(this.transactionLogIndex));
		}
	}
	
	public void setTransactionLogIndex(String transactionLogIndex) {
		this.transactionLogIndex = transactionLogIndex;
	}
}



package conflux.web3j.request;

import java.math.BigInteger;
import java.util.List;

import org.web3j.utils.Numeric;

public class LogFilter {
	private Epoch fromEpoch;
	private Epoch toEpoch;
	private List<String> blockHashes;
	private List<String> address;
	private List<List<String>> topics;
	private Long limit;
	
	public Epoch getFromEpoch() {
		return fromEpoch;
	}
	
	public void setFromEpoch(Epoch fromEpoch) {
		this.fromEpoch = fromEpoch;
	}
	
	public Epoch getToEpoch() {
		return toEpoch;
	}
	
	public void setToEpoch(Epoch toEpoch) {
		this.toEpoch = toEpoch;
	}
	
	public List<String> getBlockHashes() {
		return blockHashes;
	}
	
	public void setBlockHashes(List<String> blockHashes) {
		this.blockHashes = blockHashes;
	}
	
	public List<String> getAddress() {
		return address;
	}
	
	public void setAddress(List<String> address) {
		this.address = address;
	}
	
	public List<List<String>> getTopics() {
		return topics;
	}
	
	public void setTopics(List<List<String>> topics) {
		this.topics = topics;
	}
	
	public String getLimit() {
		if (this.limit == null) {
			return null;
		} else {
			return Numeric.encodeQuantity(BigInteger.valueOf(this.limit));
		}
	}
	
	public void setLimit(Long limit) {
		this.limit = limit;
	}
}

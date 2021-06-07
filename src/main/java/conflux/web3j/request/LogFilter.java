package conflux.web3j.request;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

import conflux.web3j.types.Address;
import org.web3j.utils.Numeric;

public class LogFilter {
	private Epoch fromEpoch;
	private Epoch toEpoch;
	private List<String> blockHashes;
	private List<Address> address;
	private List<List<String>> topics;
	private Long limit;
	private Long offset;
	
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
	
	public List<Address> getAddress() {
		return address;
	}
	
	public void setAddress(List<Address> address) {
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

	public String getOffset() {
		if (this.offset == null) {
			return null;
		} else {
			return Numeric.encodeQuantity(BigInteger.valueOf(this.offset));
		}
	}

	public void setOffset(Long offset) {
		this.offset = offset;
	}
}

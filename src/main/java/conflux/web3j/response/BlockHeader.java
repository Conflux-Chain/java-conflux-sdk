package conflux.web3j.response;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.web3j.utils.Numeric;

public class BlockHeader {
	private String hash;
	private String parentHash;
	private String height;
	private String miner;
	private String deferredStateRoot;
	private String deferredReceiptsRoot;
	private String deferredLogsBloomHash;
	private String blame;
	private String transactionsRoot;
	private String epochNumber;
	private String gasLimit;
	private String timestamp;
	private String difficulty;
	private List<String> refereeHashes;
	private boolean adaptive;
	private String nonce;
	private String size;
	
	public String getHash() {
		return hash;
	}
	
	public void setHash(String hash) {
		this.hash = hash;
	}
	
	public String getParentHash() {
		return parentHash;
	}
	
	public void setParentHash(String parentHash) {
		this.parentHash = parentHash;
	}
	
	public BigInteger getHeight() {
		return Numeric.decodeQuantity(this.height);
	}
	
	public void setHeight(String height) {
		this.height = height;
	}
	
	public String getMiner() {
		return miner;
	}
	
	public void setMiner(String miner) {
		this.miner = miner;
	}
	
	public String getDeferredStateRoot() {
		return deferredStateRoot;
	}
	
	public void setDeferredStateRoot(String deferredStateRoot) {
		this.deferredStateRoot = deferredStateRoot;
	}
	
	public String getDeferredReceiptsRoot() {
		return deferredReceiptsRoot;
	}
	
	public void setDeferredReceiptsRoot(String deferredReceiptsRoot) {
		this.deferredReceiptsRoot = deferredReceiptsRoot;
	}
	
	public String getDeferredLogsBloomHash() {
		return deferredLogsBloomHash;
	}
	
	public void setDeferredLogsBloomHash(String deferredLogsBloomHash) {
		this.deferredLogsBloomHash = deferredLogsBloomHash;
	}
	
	public BigInteger getBlame() {
		return Numeric.decodeQuantity(this.blame);
	}
	
	public void setBlame(String blame) {
		this.blame = blame;
	}
	
	public String getTransactionsRoot() {
		return transactionsRoot;
	}
	
	public void setTransactionsRoot(String transactionsRoot) {
		this.transactionsRoot = transactionsRoot;
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
	
	public BigInteger getGasLimit() {
		return Numeric.decodeQuantity(this.gasLimit);
	}
	
	public void setGasLimit(String gasLimit) {
		this.gasLimit = gasLimit;
	}
	
	public BigInteger getTimestamp() {
		return Numeric.decodeQuantity(this.timestamp);
	}
	
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	public BigInteger getDifficulty() {
		return Numeric.decodeQuantity(this.difficulty);
	}
	
	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}
	
	public List<String> getRefereeHashes() {
		return refereeHashes;
	}
	
	public void setRefereeHashes(List<String> refereeHashes) {
		this.refereeHashes = refereeHashes;
	}
	
	public boolean isAdaptive() {
		return adaptive;
	}
	
	public void setAdaptive(boolean adaptive) {
		this.adaptive = adaptive;
	}
	
	public BigInteger getNonce() {
		return Numeric.decodeQuantity(this.nonce);
	}
	
	public void setNonce(String nonce) {
		this.nonce = nonce;
	}
	
	public Optional<BigInteger> getSize() {
		if (this.size == null || this.size.isEmpty()) {
			return Optional.empty();
		} else {
			return Optional.of(Numeric.decodeQuantity(this.size));
		}
	}
	
	public void setSize(String size) {
		this.size = size;
	}
}

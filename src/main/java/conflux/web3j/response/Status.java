package conflux.web3j.response;

import java.math.BigInteger;

import org.web3j.utils.Numeric;

public class Status {
	
	public static class Response extends CfxResponse<Status> {}

    private String bestHash;
    private String chainId;
	private String networkId;
    private String epochNumber;
    private String blockNumber;
    private String pendingTxNumber;
    private String latestCheckpoint;
    private String latestConfirmed;
    private String latestState;
    
    public String getBestHash() {
		return bestHash;
	}
    
    public void setBestHash(String bestHash) {
		this.bestHash = bestHash;
	}
    
    public BigInteger getChainId() {
		return Numeric.decodeQuantity(this.chainId);
	}
    
    public void setChainId(String chainId) {
		this.chainId = chainId;
	}

	public BigInteger getNetworkId() {
		return Numeric.decodeQuantity(this.networkId);
	}

	public void setNetworkId(String networkId) {this.networkId = networkId;}

    public BigInteger getEpochNumber() {
		return Numeric.decodeQuantity(this.epochNumber);
	}
    
    public void setEpochNumber(String epochNumber) {
		this.epochNumber = epochNumber;
	}
    
    public BigInteger getBlockNumber() {
		return Numeric.decodeQuantity(this.blockNumber);
	}
    
    public void setBlockNumber(String blockNumber) {
		this.blockNumber = blockNumber;
	}
    
    public BigInteger getPendingTxNumber() {
		return Numeric.decodeQuantity(this.pendingTxNumber);
	}
    
    public void setPendingTxNumber(String pendingTxNumber) {
		this.pendingTxNumber = pendingTxNumber;
	}

	public BigInteger getLatestCheckpoint() {
		return Numeric.decodeQuantity(this.latestCheckpoint);
	}

	public void setLatestCheckpoint(String latestCheckpoint) {
		this.latestCheckpoint = latestCheckpoint;
	}

	public BigInteger getLatestConfirmed() {
		return Numeric.decodeQuantity(this.latestConfirmed);
	}

	public void setLatestConfirmed(String latestConfirmed) {
		this.latestConfirmed = latestConfirmed;
	}

	public BigInteger getLatestState() {
		return Numeric.decodeQuantity(this.latestState);
	}

	public void setLatestState(String latestState) {
		this.latestState = latestState;
	}

}

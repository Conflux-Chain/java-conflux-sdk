package conflux.web3j.response;

import java.math.BigInteger;

import org.web3j.utils.Numeric;

public class Status {
	
	public static class Response extends CfxResponse<Status> {}
	
    private String bestHash;
    private String chainId;
    private String epochNumber;
    private String blockNumber;
    private String pendingTxNumber;
    
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

}

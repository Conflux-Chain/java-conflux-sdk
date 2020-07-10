package conflux.web3j.response;

import java.math.BigInteger;

import org.web3j.utils.Numeric;

public class RewardInfo {
	
	public static class Response extends CfxListResponse<RewardInfo> {}
	
    private String blockHash;
    private String author;
    private String totalReward;
    private String baseReward;
    private String txFee;
    
    public String getBlockHash() {
		return blockHash;
	}
    
    public void setBlockHash(String blockHash) {
		this.blockHash = blockHash;
	}
    
    public String getAuthor() {
		return author;
	}
    
    public void setAuthor(String author) {
		this.author = author;
	}
    
    public BigInteger getTotalReward() {
		return Numeric.decodeQuantity(this.totalReward);
	}
    
    public void setTotalReward(String totalReward) {
		this.totalReward = totalReward;
	}
    
    public BigInteger getBaseReward() {
		return Numeric.decodeQuantity(this.baseReward);
	}
    
    public void setBaseReward(String baseReward) {
		this.baseReward = baseReward;
	}
    
    public BigInteger getTxFee() {
		return Numeric.decodeQuantity(this.txFee);
	}
    
    public void setTxFee(String txFee) {
		this.txFee = txFee;
	}

}

package conflux.web3j.response;

import java.math.BigInteger;

public class VoteStakeInfo {
    public static class ListResponse extends CfxListResponse<VoteStakeInfo> {}
    private String amount;
    private long unlockBlockNumber;

    public String getAmount() {return amount;}

    public void setAmount(String amount) {this.amount = amount;}

    public long getUnlockBlockNumber() {return unlockBlockNumber;}

    public void setUnlockBlockNumber(long blockNumber) {this.unlockBlockNumber = blockNumber;}
}

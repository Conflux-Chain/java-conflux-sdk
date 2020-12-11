package conflux.web3j.response;

import org.web3j.utils.Numeric;

import java.math.BigInteger;

public class VoteStakeInfo {
    public static class ListResponse extends CfxListResponse<VoteStakeInfo> {}
    private String amount;
    private String unlockBlockNumber;

    public BigInteger getAmount() {return Numeric.decodeQuantity(amount);}

    public void setAmount(String amount) {this.amount = amount;}

    public BigInteger getUnlockBlockNumber() {return Numeric.decodeQuantity(unlockBlockNumber);}

    public void setUnlockBlockNumber(String blockNumber) {this.unlockBlockNumber = blockNumber;}
}

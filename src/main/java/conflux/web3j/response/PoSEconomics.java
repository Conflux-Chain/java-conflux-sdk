package conflux.web3j.response;

import org.web3j.utils.Numeric;

import java.math.BigInteger;

public class PoSEconomics {
    public static class Response extends CfxResponse<PoSEconomics> {}

    private String distributablePosInterest;
    private String lastDistributeBlock;
    private String totalPosStakingTokens;

    public BigInteger getDistributablePosInterest() {
        return Numeric.decodeQuantity(this.distributablePosInterest);
    }

    public void setDistributablePosInterest(String distributablePosInterest) {
        this.distributablePosInterest = distributablePosInterest;
    }

    public BigInteger getLastDistributeBlock() {
        return Numeric.decodeQuantity(this.lastDistributeBlock);
    }

    public void setLastDistributeBlock(String lastDistributeBlock) {
        this.distributablePosInterest = lastDistributeBlock;
    }

    public BigInteger getTotalPosStakingTokens() {
        return Numeric.decodeQuantity(this.totalPosStakingTokens);
    }

    public void setTotalPosStakingTokens(String totalPosStakingTokens) {
        this.distributablePosInterest = totalPosStakingTokens;
    }
}

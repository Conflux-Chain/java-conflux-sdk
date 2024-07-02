package conflux.web3j.response;

import org.web3j.utils.Numeric;

import java.math.BigInteger;

public class ParamsOfVote {
    public static class Response extends CfxResponse<ParamsOfVote> {}

    private String baseFeeShareProp;
    private String interestRate;
    private String powBaseReward;
    private String storagePointProp;

    public BigInteger getBaseFeeShareProp() {
        return Numeric.decodeQuantity(baseFeeShareProp);
    }

    public void setBaseFeeShareProp(String baseFeeShareProp) {
        this.baseFeeShareProp = baseFeeShareProp;
    }

    public BigInteger getInterestRate() {
        return Numeric.decodeQuantity(interestRate);
    }

    public void setInterestRate(String interestRate) {
        this.interestRate = interestRate;
    }

    public BigInteger getPowBaseReward() {
        return Numeric.decodeQuantity(powBaseReward);
    }

    public void setPowBaseReward(String powBaseReward) {
        this.powBaseReward = powBaseReward;
    }

    public BigInteger getStoragePointProp() {
        return Numeric.decodeQuantity(storagePointProp);
    }

    public void setStoragePointProp(String storagePointProp) {
        this.storagePointProp = storagePointProp;
    }
}

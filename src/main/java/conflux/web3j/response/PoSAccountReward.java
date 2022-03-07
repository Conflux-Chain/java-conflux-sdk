package conflux.web3j.response;

import org.web3j.utils.Numeric;

import java.math.BigInteger;

public class PoSAccountReward {
    public static class Response extends CfxResponse<PoSAccountReward> {}

    private String posAddress;
    private String powAddress;
    private String reward;

    public String getPosAddress() {
        return posAddress;
    }

    public void setPosAddress(String posAddress) {
        this.posAddress = posAddress;
    }

    public String getPowAddress() {
        return powAddress;
    }

    public void setPowAddress(String posAddress) {
        this.powAddress = powAddress;
    }

    public BigInteger getReward() {
        return Numeric.decodeQuantity(this.reward);
    }

    public void setReward(String reward) {
        this.reward = reward;
    }
}

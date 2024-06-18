package conflux.web3j.response;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

public class FeeHistory {
    public static class Response extends CfxResponse<FeeHistory> {}

    private String oldestEpoch;
    private List<String> baseFeePerGas;
    private List<Float> gasUsedRatio;
    private List<String> reward;

    public BigInteger getOldestEpoch() {
        return Numeric.decodeQuantity(oldestEpoch);
    }

    public void setOldestEpoch(String oldestEpoch) {
        this.oldestEpoch = oldestEpoch;
    }

    public List<BigInteger> getBaseFeePerGas() {
        return baseFeePerGas.stream().map(Numeric::decodeQuantity).collect(Collectors.toList());
    }

    public void setBaseFeePerGas(List<String> baseFeePerGas) {
        this.baseFeePerGas = baseFeePerGas;
    }

    public List<Float> getGasUsedRatio() {
        return gasUsedRatio;
    }

    public void setGasUsedRatio(List<Float> gasUsedRatio) {
        this.gasUsedRatio = gasUsedRatio;
    }

    public List<BigInteger> getReward() {
        return reward.stream().map(Numeric::decodeQuantity).collect(Collectors.toList());
    }

    public void setReward(List<String> reward) {
        this.reward = reward;
    }
}

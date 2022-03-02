package conflux.web3j.response;

import java.util.List;

public class PoSEpochRewards {
    public static class Response extends CfxResponse<PoSEpochRewards> {}

    private String powEpochHash;
    private List<PoSAccountReward> accountRewards;

    public String getPowEpochHash() {
        return powEpochHash;
    }

    public void setPowEpochHash(String powEpochHash) {
        this.powEpochHash = powEpochHash;
    }

    public List<PoSAccountReward> getAccountRewards() {
        return accountRewards;
    }
}

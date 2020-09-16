package conflux.web3j.response.events;

import java.util.List;

public class NewHead {
    private String adaptive;
    private int blame;
    private String deferredLogsBloomHash;
    private String deferredReceiptsRoot;
    private String deferredStateRoot;
    private String difficulty;
    private String epochNumber;
    private String gasLimit;
    private String hash;
    private String height;
    private String miner;
    private String nonce;
    private String parentHash;
    private String powQuality;
    private List<String> refereeHashes;
    private String timestamp;
    private String transactionsRoot;

    public String getAdaptive() {
        return adaptive;
    }

    public int getBlame() {
        return blame;
    }

    public String getDeferredLogsBloomHash() {
        return deferredLogsBloomHash;
    }

    public String getDeferredReceiptsRoot() {
        return deferredReceiptsRoot;
    }

    public String getDeferredStateRoot() {
        return deferredStateRoot;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getEpochNumber() {
        return epochNumber;
    }

    public String getGasLimit() {
        return gasLimit;
    }

    public String getHeight() {
        return height;
    }

    public String getHash() {
        return hash;
    }

    public String getMiner() {
        return miner;
    }

    public String getNonce() {
        return nonce;
    }

    public String getParentHash() {
        return parentHash;
    }

    public String getPowQuality() {
        return powQuality;
    }

    public List<String> getRefereeHashes() {
        return refereeHashes;
    }

    public String getTimestamp() { return timestamp; }

    public String getTransactionsRoot() {
        return transactionsRoot;
    }
}

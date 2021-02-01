package conflux.web3j.response.events;

import java.util.List;
import java.math.BigInteger;

import conflux.web3j.types.Address;
import org.web3j.utils.Numeric;

public class NewHead {
    private boolean adaptive;
    private String blame;
    private String deferredLogsBloomHash;
    private String deferredReceiptsRoot;
    private String deferredStateRoot;
    private String difficulty;
    private String epochNumber;
    private String gasLimit;
    private String hash;
    private String height;
    private Address miner;
    private String nonce;
    private String parentHash;
    private String powQuality;
    private List<String> refereeHashes;
    private String timestamp;
    private String transactionsRoot;

    public boolean getAdaptive() {
        return adaptive;
    }

    public int getBlame() {
        return Numeric.decodeQuantity(blame).intValue();
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

    public BigInteger getDifficulty() { return Numeric.decodeQuantity(difficulty); }

    public BigInteger getEpochNumber() {
        return Numeric.decodeQuantity(epochNumber);
    }

    public BigInteger getGasLimit() {
        return Numeric.decodeQuantity(gasLimit);
    }

    public BigInteger getHeight() {
        return Numeric.decodeQuantity(height);
    }

    public String getHash() {
        return hash;
    }

    public Address getMiner() {
        return miner;
    }

    public BigInteger getNonce() {
        return Numeric.decodeQuantity(nonce);
    }

    public String getParentHash() {
        return parentHash;
    }

    public BigInteger getPowQuality() {
        return Numeric.decodeQuantity(powQuality);
    }

    public List<String> getRefereeHashes() {
        return refereeHashes;
    }

    public BigInteger getTimestamp() { return Numeric.decodeQuantity(timestamp); }

    public String getTransactionsRoot() {
        return transactionsRoot;
    }
}

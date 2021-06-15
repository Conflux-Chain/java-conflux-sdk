package conflux.web3j.request;

import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.List;

public class TraceFilter {
    private Epoch fromEpoch;
    private Epoch toEpoch;
    private List<String> blockHashes;
    private List<String> actionTypes;
    private Long after;
    private Long count;

    public List<String> getActionTypes() {
        return actionTypes;
    }

    public void setActionTypes(List<String> actionTypes) {
        this.actionTypes = actionTypes;
    }

    public Epoch getFromEpoch() {
        return fromEpoch;
    }

    public void setFromEpoch(Epoch fromEpoch) {
        this.fromEpoch = fromEpoch;
    }

    public Epoch getToEpoch() {
        return toEpoch;
    }

    public void setToEpoch(Epoch toEpoch) {
        this.toEpoch = toEpoch;
    }

    public List<String> getBlockHashes() {
        return blockHashes;
    }

    public void setBlockHashes(List<String> blockHashes) {
        this.blockHashes = blockHashes;
    }

    public String getAfter() {
        if (this.after == null) {
            return null;
        } else {
            return Numeric.encodeQuantity(BigInteger.valueOf(this.after));
        }
    }

    public void setAfter(Long limit) {
        this.after = limit;
    }

    public String getCount() {
        if (this.count == null) {
            return null;
        } else {
            return Numeric.encodeQuantity(BigInteger.valueOf(this.count));
        }
    }

    public void setCount(Long offset) {
        this.count = offset;
    }
}

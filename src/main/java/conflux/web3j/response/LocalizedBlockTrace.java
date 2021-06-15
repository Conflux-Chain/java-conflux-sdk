package conflux.web3j.response;

import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.List;

public class LocalizedBlockTrace {
    public static class Response extends CfxNullableResponse<LocalizedBlockTrace> {}

    private List<BlockTxTrace> transactionTraces;
    private String epochHash;
    private String epochNumber;
    private String blockHash;

    public List<BlockTxTrace> getTransactionTraces() {
        return transactionTraces;
    }

    public void setTransactionTraces(List<BlockTxTrace> transactionTraces) {
        this.transactionTraces = transactionTraces;
    }

    public String getEpochHash() {
        return epochHash;
    }

    public void setEpochHash(String epochHash) {
        this.epochHash = epochHash;
    }

    public BigInteger getEpochNumber() {
        return Numeric.decodeQuantity(epochNumber);
    }

    public void setEpochNumber(String epochNumber) {
        this.epochNumber = epochNumber;
    }

    public String getBlockHash() {
        return blockHash;
    }

    public void setBlockHash(String blockHash) {
        this.blockHash = blockHash;
    }
}

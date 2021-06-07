package conflux.web3j.response;

import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.List;

public class LocalizedBlockTrace {
    public static class Response extends CfxNullableResponse<LocalizedBlockTrace> {}

    private List<LocalizedTransactionTrace> transactionTraces;
    private String epochHash;
    private String epochNumber;
    private String blockHash;

    public List<LocalizedTransactionTrace> getTransactionTraces() {
        return transactionTraces;
    }

    public void setTransactionTraces(List<LocalizedTransactionTrace> transactionTraces) {
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

    public class LocalizedTransactionTrace {
        public List<BlockLocalizedTrace> getTraces() {
            return traces;
        }

        public void setTraces(List<BlockLocalizedTrace> traces) {
            this.traces = traces;
        }

        public BigInteger getTransactionPosition() {
            return Numeric.decodeQuantity(transactionPosition);
        }

        public void setTransactionPosition(String transactionPosition) {
            this.transactionPosition = transactionPosition;
        }

        public String getTransactionHash() {
            return transactionHash;
        }

        public void setTransactionHash(String transactionHash) {
            this.transactionHash = transactionHash;
        }

        private List<BlockLocalizedTrace> traces;
        private String transactionPosition;
        private String transactionHash;
    }

    public class BlockLocalizedTrace {
        public Action getAction() {
            return action;
        }

        public void setAction(Action action) {
            this.action = action;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        private Action action;
        private String type;
    }
}

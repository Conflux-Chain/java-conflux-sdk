package conflux.web3j.response;

import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.List;

public class BlockTxTrace {
    public List<BlockTxInnerTrace> getTraces() {
        return traces;
    }

    public void setTraces(List<BlockTxInnerTrace> traces) {
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

    private List<BlockTxInnerTrace> traces;
    private String transactionPosition;
    private String transactionHash;
}

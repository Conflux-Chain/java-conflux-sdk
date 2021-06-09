package conflux.web3j.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import conflux.web3j.utils.ToStringUtils;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.List;

public class LocalizedTrace {
    public static class Response extends CfxNullableResponse<List<LocalizedTrace>> {}

    public Action getAction() {
        return action;
    }

    public String getEpochHash() {
        return epochHash;
    }

    public BigInteger getEpochNumber() {
        return Numeric.decodeQuantity(epochNumber);
    }

    public String getBlockHash() {
        return blockHash;
    }

    public BigInteger getTransactionPosition() {
        return Numeric.decodeQuantity(transactionPosition);
    }

    public String getTransactionHash() {
        return transactionHash;
    }

    public String getType() {
        return type;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public void setEpochHash(String epochHash) {
        this.epochHash = epochHash;
    }

    public void setEpochNumber(String epochNumber) {
        this.epochNumber = epochNumber;
    }

    public void setBlockHash(String blockHash) {
        this.blockHash = blockHash;
    }

    public void setTransactionPosition(String transactionPosition) {
        this.transactionPosition = transactionPosition;
    }

    public void setTransactionHash(String transactionHash) {
        this.transactionHash = transactionHash;
    }

    public void setType(String type) {
        this.type = type;
    }

    private Action action;
    private String type;
    private String epochHash;
    private String epochNumber;
    private String blockHash;
    private String transactionPosition;
    private String transactionHash;

    @Override
    public String toString() {
        String result = "";
        result += "epochHash: " + this.epochHash;
        result += "\nepochNumber: " + this.epochNumber;
        result += "\nblockHash: " + this.blockHash;
        result += "\ntransactionPosition: " + this.transactionPosition;
        result += "\ntransactionHash: " + this.transactionHash;
        result += "\ntype: " + this.type;
        result += "\naction: " + this.action;
        return  result;
    }
}

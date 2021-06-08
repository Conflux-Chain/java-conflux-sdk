package conflux.web3j.response;

import conflux.web3j.types.Address;
import org.web3j.utils.Numeric;

import java.math.BigInteger;

// TODO There are five kind actions(call, callResult, create, createResult, internalTraces), how to express them in one class
public class Action {
    // call
    private String callType;
    private Address from;
    private String gas;
    private String input;
    private Address to;
    private String value;
    // create
    private String init;
    // call result
    private String outcome;
    private String gasLeft;
    private String returnData;
    // create result
    private String addr;

    public BigInteger getGas() {
        return Numeric.decodeQuantity(gas);
    }

    public String getInput() {
        return input;
    }

    public Address getTo() {
        return to;
    }

    public BigInteger getValue() {
        return Numeric.decodeQuantity(value);
    }

    public String getInit() {
        return init;
    }

    public String getOutcome() {
        return outcome;
    }

    public BigInteger getGasLeft() {
        return Numeric.decodeQuantity(gasLeft);
    }

    public String getReturnData() {
        return returnData;
    }

    public String getAddr() {
        return addr;
    }

    public void setGas(String gas) {
        this.gas = gas;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public void setTo(Address to) {
        this.to = to;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setInit(String init) {
        this.init = init;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public void setGasLeft(String gasLeft) {
        this.gasLeft = gasLeft;
    }

    public void setReturnData(String returnData) {
        this.returnData = returnData;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }

    public String getCallType() {
        return this.callType;
    }

    public void setFrom(Address from) {
        this.from = from;
    }

    public Address getFrom() {
        return this.from;
    }

    @Override
    public String toString() {
        String result = "";
        result += "callType: " + this.callType;
        result += "\nfrom: " + this.from;
        result += "\ngas: " + this.gas;
        result += "\ninput: " + this.input;
        result += "\nto: " + this.to;
        result += "\nvalue: " + this.value;
        return result;
    }
}

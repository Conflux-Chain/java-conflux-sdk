package conflux.web3j.response;

import org.web3j.utils.Numeric;

import java.math.BigInteger;

public class DepositInfo {
    public static class ListResponse extends CfxListResponse<DepositInfo> {}

    private String amount;
    private String depositTime;
    private String accumulatedInterestRate;

    public BigInteger getAmount() {return Numeric.decodeQuantity(amount);}

    public void setAmount(String amount) {this.amount = amount;}

    public BigInteger getDepositTime() {return Numeric.decodeQuantity(depositTime);}

    public void setDepositTime(String time) {this.depositTime = time;}

    public BigInteger getAccumulatedInterestRate() {return Numeric.decodeQuantity(accumulatedInterestRate);}

    public void setAccumulatedInterestRate(String rate) {this.accumulatedInterestRate = rate;}
}

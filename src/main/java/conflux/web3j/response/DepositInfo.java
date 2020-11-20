package conflux.web3j.response;

import java.math.BigInteger;

public class DepositInfo {
    public static class ListResponse extends CfxListResponse<DepositInfo> {}

    private String amount;
    private long depositTime;
    private String accumulatedInterestRate;

    public String getAmount() {return amount;}

    public void setAmount(String amount) {this.amount = amount;}

    public long getDepositTime() {return depositTime;}

    public void setDepositTime(long time) {this.depositTime = time;}

    public String getAccumulatedInterestRate() {return accumulatedInterestRate;}

    public void setAccumulatedInterestRate(String rate) {this.accumulatedInterestRate = rate;}
}

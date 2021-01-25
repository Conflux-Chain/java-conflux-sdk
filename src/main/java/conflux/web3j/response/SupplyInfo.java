package conflux.web3j.response;

import org.web3j.utils.Numeric;

import java.math.BigInteger;

public class SupplyInfo {
    public static class Response extends CfxResponse<SupplyInfo> {}

    private String totalIssued;
    private String totalStaking;
    private String totalCollateral;
    private String totalCirculating;

    public BigInteger getTotalIssued() {
        return Numeric.decodeQuantity(totalIssued);
    }

    public void setTotalIssued(String totalIssued) {
        this.totalIssued = totalIssued;
    }

    public BigInteger getTotalStaking() {
        return Numeric.decodeQuantity(totalStaking);
    }

    public void setTotalStaking(String totalStaking) {
        this.totalStaking = totalStaking;
    }

    public BigInteger getTotalCollateral () {
        return Numeric.decodeQuantity(totalCollateral);
    }

    public void setTotalCollateral(String totalCollateral) {
        this.totalCollateral = totalCollateral;
    }

    public BigInteger getTotalCirculating () {
        return Numeric.decodeQuantity(totalCirculating);
    }

    public void setTotalCirculating(String totalCirculating) {
        this.totalCirculating = totalCirculating;
    }

    @Override
    public String toString() {
        String formatStr = "{totalIssued = %s, totalStaking = %s, totalCollateral = %s, totalCirculating = %s}";
        return String.format(formatStr, this.getTotalIssued(), this.getTotalStaking(), this.getTotalCollateral(), this.getTotalCirculating());
    }
}

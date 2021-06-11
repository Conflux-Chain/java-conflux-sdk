package conflux.web3j.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import conflux.web3j.utils.Utils;
import org.web3j.utils.Numeric;

import java.math.BigInteger;

public class AccountPendingInfo {
    public static class Response extends CfxNullableResponse<AccountPendingInfo> {}

    private String localNonce;
    private String pendingCount;
    private String pendingNonce;
    private String nextPendingTx;

    public BigInteger getPendingNonce() {
        return Numeric.decodeQuantity(this.pendingNonce);
    }

    public void setPendingNonce(String pendingNonce) {
        this.pendingNonce = pendingNonce;
    }

    public BigInteger getLocalNonce() {
        return Numeric.decodeQuantity(this.localNonce);
    }

    public void setLocalNonce(String localNonce) {
        this.localNonce = localNonce;
    }

    public BigInteger getPendingCount() {
        return Numeric.decodeQuantity(this.pendingCount);
    }

    public void setPendingCount(String pendingCount) {
        this.pendingCount = pendingCount;
    }

    public String getNextPendingTx() {
        return this.nextPendingTx;
    }

    public void setNextPendingTx(String nextPendingTx) {
        this.nextPendingTx = nextPendingTx;
    }

    @Override
    public String toString() {
        return Utils.jsonStringify(this);
    }
}

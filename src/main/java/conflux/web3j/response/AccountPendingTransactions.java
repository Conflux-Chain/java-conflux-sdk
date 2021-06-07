package conflux.web3j.response;

import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.List;

public class AccountPendingTransactions {
    public static class Response extends CfxResponse<AccountPendingTransactions> {}

    private String pendingCount;
    private TxPendingStatus firstTxStatus;
    private List<Transaction> pendingTransactions;

    public BigInteger getPendingCount() {
        return Numeric.decodeQuantity(this.pendingCount);
    }

    public void setPendingCount(String pendingCount) {
        this.pendingCount = pendingCount;
    }

    public class TxPendingStatus {
        private String pending;

        public String getPending() {return this.pending;}

        public void setPending(String pending) {this.pending = pending;}
    }
}

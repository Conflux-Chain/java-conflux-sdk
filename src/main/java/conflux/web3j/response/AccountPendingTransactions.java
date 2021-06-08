package conflux.web3j.response;

import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public class AccountPendingTransactions {
    public static class Response extends CfxResponse<AccountPendingTransactions> {}

    private String pendingCount;
    private TxPendingStatus firstTxStatus;
    private List<Transaction> pendingTransactions;

    public Optional<TxPendingStatus> getFirstTxStatus() {
        if (this.firstTxStatus == null) {
            return Optional.empty();
        } else {
            return Optional.of(this.firstTxStatus);
        }
    }

    public void setFirstTxStatus(TxPendingStatus firstTxStatus) {
        this.firstTxStatus = firstTxStatus;
    }

    public List<Transaction> getPendingTransactions() {
        return pendingTransactions;
    }

    public void setPendingTransactions(List<Transaction> pendingTransactions) {
        this.pendingTransactions = pendingTransactions;
    }

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

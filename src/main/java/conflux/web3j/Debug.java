package conflux.web3j;

import conflux.web3j.request.Epoch;
import conflux.web3j.response.AccountPendingInfo;
import conflux.web3j.response.AccountPendingTransactions;
import conflux.web3j.response.Receipt;
import conflux.web3j.types.Address;

import java.io.Closeable;
import java.util.List;
import java.util.Optional;

// debug RPC methods
public interface Debug extends Closeable {
    Request<Optional<List<List<Receipt>>>, Receipt.ListResponse> getEpochReceipts(Epoch epoch);
}

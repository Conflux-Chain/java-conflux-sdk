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
    Request<List<List<Receipt>>, Receipt.ListResponse> getEpochReceipt(Epoch epoch);

    Request<Optional<AccountPendingInfo>, AccountPendingInfo.Response> getAccountPendingInfo(Address address);

    Request<AccountPendingTransactions, AccountPendingTransactions.Response> getAccountPendingTransactions(Address address);
}

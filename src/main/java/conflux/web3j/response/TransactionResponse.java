package conflux.web3j.response;

import java.util.Optional;

import org.web3j.protocol.core.Response;

import conflux.web3j.HasValue;

public class TransactionResponse extends Response<Transaction> implements HasValue<Optional<Transaction>> {

	@Override
	public Optional<Transaction> getValue() {
		return Optional.ofNullable(this.getResult());
	}

}

package conflux.web3j.response;

import java.util.Optional;

import org.web3j.protocol.core.Response;

import conflux.web3j.HasValue;

public class ReceiptResponse extends Response<Receipt> implements HasValue<Optional<Receipt>> {

	@Override
	public Optional<Receipt> getValue() {
		return Optional.ofNullable(this.getResult());
	}

}

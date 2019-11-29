package conflux.web3j.response;

import java.util.Optional;

import org.web3j.protocol.core.Response;

import conflux.web3j.HasValue;

public class BlockResponse extends Response<Block> implements HasValue<Optional<Block>> {

	@Override
	public Optional<Block> getValue() {
		return Optional.ofNullable(this.getResult());
	}

}

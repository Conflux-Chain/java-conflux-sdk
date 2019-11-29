package conflux.web3j.response;

import java.util.Optional;

import org.web3j.protocol.core.Response;

import conflux.web3j.HasValue;

public class BlockSummaryResponse extends Response<BlockSummary> implements HasValue<Optional<BlockSummary>> {

	@Override
	public Optional<BlockSummary> getValue() {
		return Optional.ofNullable(this.getResult());
	}

}

package conflux.web3j.response;

import java.util.ArrayList;
import java.util.List;

import org.web3j.protocol.core.Response;

import conflux.web3j.HasValue;

public class BlocksResponse extends Response<List<String>> implements HasValue<List<String>> {

	@Override
	public List<String> getValue() {
		List<String> blocks = this.getResult();
		return blocks == null ? new ArrayList<String>() : blocks;
	}

}

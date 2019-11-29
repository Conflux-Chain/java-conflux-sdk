package conflux.web3j.response;

import org.web3j.protocol.core.Response;

import conflux.web3j.HasValue;

public class StringResponse extends Response<String> implements HasValue<String> {

	@Override
	public String getValue() {
		return this.getResult();
	}
	
}

package conflux.web3j.response;

import java.util.ArrayList;
import java.util.List;

import org.web3j.protocol.core.Response;

import conflux.web3j.HasValue;

public class LogsResponse extends Response<List<Log>> implements HasValue<List<Log>> {

	@Override
	public List<Log> getValue() {
		List<Log> logs = this.getResult();
		return logs == null ? new ArrayList<Log>() : logs;
	}

}

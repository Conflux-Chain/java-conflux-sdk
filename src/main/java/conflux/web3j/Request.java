package conflux.web3j;

import java.io.IOException;
import java.util.Arrays;

import org.web3j.protocol.Web3jService;
import org.web3j.protocol.core.Response;

public class Request<T, R extends Response<?> & HasValue<T>> extends org.web3j.protocol.core.Request<Object, R> {
	
	public Request() {
	}
	
	public Request(Web3jService service, String method, Class<R> responseType, Object... params) {
		super(method, Arrays.asList(params), service, responseType);
	}
	
	public T sendAndGet() throws RpcException {
		try {
			R response = this.send();
			if (response.getError() != null) {
				throw new RpcException(response.getError());
			}
			
			return response.getValue();
		} catch (IOException e) {
			throw RpcException.sendFailure(e);
		}
	}

}

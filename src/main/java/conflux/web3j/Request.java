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
	
	private T getResult(R response) throws RpcException {
		if (response.getError() != null) {
			throw new RpcException(response.getError());
		}
		
		return response.getValue();
	}
	
	public T sendAndGet() throws RpcException {
		return this.sendAndGet(0);
	}
	
	public T sendAndGet(int retry) throws RpcException {
		R response = null;
		
		while (response == null) {
			try {
				response = this.send();
			} catch (IOException e) {
				if (retry <= 0) {
					throw RpcException.sendFailure(e);
				}
				
				retry--;
			}
		}
		
		return this.getResult(response);
	}
	
	public T sendAndGet(int retry, long intervalMills) throws RpcException, InterruptedException {
		R response = null;
		
		while (response == null) {
			try {
				response = this.send();
			} catch (IOException e) {
				if (retry <= 0) {
					throw RpcException.sendFailure(e);
				}
				
				retry--;
				
				if (intervalMills > 0) {
					Thread.sleep(intervalMills);
				}
			}
		}
		
		return this.getResult(response);
	}

}

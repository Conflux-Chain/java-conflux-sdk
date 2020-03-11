package conflux.web3j;

import java.io.IOException;

import org.web3j.protocol.core.Response.Error;

public class RpcException extends RuntimeException {

	private static final long serialVersionUID = 4965906647640407824L;
	
	public static final Error ERROR_IO_SEND = new Error(-1, "failed to send RPC request (IO error)");
	public static final Error ERROR_INTERRUPTED = new Error(-2, "failed to send RPC request (interrupted)");
	
	private Error error;
	
	public RpcException() {
	}
	
	public RpcException(Error error) {
		super(buildErrorMessage(error));
		
		this.error = error;
	}
	
	public RpcException(Error error, Throwable cause) {
		super(buildErrorMessage(error), cause);
		
		this.error = error;
	}
	
	private static String buildErrorMessage(Error error) {
		return String.format("RPC error: code = %d, message = %s, data = %s", error.getCode(), error.getMessage(), error.getData());
	}
	
	private static RpcException predefined(Error template, Throwable e) {
		Error error = new Error(template.getCode(), template.getMessage());
		error.setData(e.getMessage());
		return new RpcException(error, e);
	}
	
	public static RpcException sendFailure(IOException e) {
		return predefined(ERROR_IO_SEND, e);
	}
	
	public static RpcException interrupted(InterruptedException e) {
		return predefined(ERROR_INTERRUPTED, e);
	}

	public Error getError() {
		return error;
	}

}

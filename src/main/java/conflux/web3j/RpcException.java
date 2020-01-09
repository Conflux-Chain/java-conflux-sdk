package conflux.web3j;

import java.io.IOException;

import org.web3j.protocol.core.Response.Error;

public class RpcException extends RuntimeException {

	private static final long serialVersionUID = 4965906647640407824L;
	
	public static final int ERROR_CODE_IO_SEND = -1;
	private static final String ERROR_MESSAGE_IO_SEND = "failed to send RPC request";
	
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
	
	public static RpcException sendFailure(IOException e) {
		Error error = new Error(ERROR_CODE_IO_SEND, ERROR_MESSAGE_IO_SEND);
		error.setData(e.getMessage());
		return new RpcException(error, e);
	}

	public Error getError() {
		return error;
	}

}

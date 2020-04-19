package conflux.web3j.types;

import org.web3j.protocol.core.Response.Error;

public class SendTransactionResult {
	
	private String txHash;
	private SendTransactionError errorType;
	private Error rawError;
	
	public SendTransactionResult(String txHash) {
		this.txHash = txHash;
	}
	
	public SendTransactionResult(Error error) {
		this.errorType = SendTransactionError.parse(error);
		this.rawError = error;
	}
	
	public String getTxHash() {
		return txHash;
	}
	
	public SendTransactionError getErrorType() {
		return errorType;
	}
	
	public Error getRawError() {
		return rawError;
	}
	
	@Override
	public String toString() {
		if (this.txHash != null) {
			return this.txHash;
		}
		
		return String.format("RPC error: type = %s, code = %s, message = %s, data = %s",
				this.errorType,
				this.rawError.getCode(),
				this.rawError.getMessage(),
				this.rawError.getData());
	}

}

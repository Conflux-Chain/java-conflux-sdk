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

}

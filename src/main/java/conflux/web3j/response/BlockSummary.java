package conflux.web3j.response;

import java.util.List;

public class BlockSummary extends BlockHeader {
	
	public static class Response extends CfxNullableResponse<BlockSummary> {}
	
	private List<String> transactions;

	public List<String> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<String> transactions) {
		this.transactions = transactions;
	} 
}

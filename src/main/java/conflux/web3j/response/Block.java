package conflux.web3j.response;

import java.util.List;

public class Block extends BlockHeader {
	
	public static class Response extends CfxNullableResponse<Block> {}
	
	public static class ListResponse extends CfxListResponse<String> {}
	
	private List<Transaction> transactions;

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}
}

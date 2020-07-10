package conflux.web3j.response;

public class StorageRoot {
	
	public static class Response extends CfxNullableResponse<StorageRoot> {}
	
	private String delta;
	private String intermediate;
	private String snapshot;
	
	public String getDelta() {
		return delta;
	}
	
	public void setDelta(String delta) {
		this.delta = delta;
	}
	
	public String getIntermediate() {
		return intermediate;
	}
	
	public void setIntermediate(String intermediate) {
		this.intermediate = intermediate;
	}
	
	public String getSnapshot() {
		return snapshot;
	}
	
	public void setSnapshot(String snapshot) {
		this.snapshot = snapshot;
	}

}

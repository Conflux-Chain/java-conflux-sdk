package conflux.web3j.response;

import java.util.List;

public class LogEntry {
	private String address;
	private List<String> topics;
	private List<Byte> data;
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public List<String> getTopics() {
		return topics;
	}
	
	public void setTopics(List<String> topics) {
		this.topics = topics;
	}
	
	public List<Byte> getData() {
		return data;
	}
	
	public void setData(List<Byte> data) {
		this.data = data;
	}
}

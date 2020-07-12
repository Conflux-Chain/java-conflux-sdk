package conflux.web3j.request;

import java.math.BigInteger;

import org.web3j.utils.Numeric;

import com.fasterxml.jackson.annotation.JsonValue;

import conflux.web3j.HasValue;

public interface Epoch extends HasValue<String> {	
	static Epoch earliest() {
		return DefaultEpoch.EARLIEST;
	}
	
	static Epoch latestState() {
		return DefaultEpoch.LATEST_STATE;
	}
	
	static Epoch latestMined() {
		return DefaultEpoch.LATEST_MINED;
	}
	
	static Epoch latestCheckpoint() {
		return DefaultEpoch.LATEST_CHECKPOINT;
	}
	
	static Epoch latestConfirmed() {
		return DefaultEpoch.LATEST_CONFIRMED;
	}
	
	static Epoch numberOf(long number) {
		String value = Numeric.encodeQuantity(BigInteger.valueOf(number));
		return new EpochByValue(value);
	}
	
	static Epoch numberOf(BigInteger number) {
		String value = Numeric.encodeQuantity(number);
		return new EpochByValue(value);
	}
	
	static Epoch hashOf(String blockHash) {
		return new EpochByValue(blockHash);
	}
}

enum DefaultEpoch implements Epoch {
	EARLIEST("earliest"),
	LATEST_STATE("latest_state"),
	LATEST_MINED("latest_mined"),
	LATEST_CHECKPOINT("latest_checkpoint"),
	LATEST_CONFIRMED("latest_confirmed");
	
	private String value;
	
	DefaultEpoch(String value) {
		this.value = value;
	}

	@JsonValue
	@Override
	public String getValue() {
		return this.value;
	}
}

class EpochByValue implements Epoch {
	private String value;
	
	public EpochByValue(String value) {
		this.value = value;
	}

	@JsonValue
	@Override
	public String getValue() {
		return this.value;
	}
}

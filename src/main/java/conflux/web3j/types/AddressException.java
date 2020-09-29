package conflux.web3j.types;

public class AddressException extends RuntimeException {

	private static final long serialVersionUID = 2338294090416527939L;

	public static final String INVALID_PREFIX = "HEX prefix 0x missed";
	public static final String INVALID_LENGTH = "wrong length";
	public static final String INVALID_TYPE = "wrong type";
	public static final String INVALID_HEX = "wrong HEX format";

	private String reason;

	public AddressException() {
	}

	public AddressException(String reason) {
		super(String.format("invalid address (%s)", reason));

		this.reason = reason;
	}

	public String getReason() {
		return reason;
	}

}

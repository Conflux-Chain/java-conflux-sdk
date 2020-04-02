package conflux.web3j.types;

public class AddressException extends RuntimeException {

	private static final long serialVersionUID = 2338294090416527939L;

	public static final AddressException INVALID_PREFIX = new AddressException("HEX prefix 0x missed");
	public static final AddressException INVALID_LENGTH = new AddressException("wrong length");
	public static final AddressException INVALID_TYPE = new AddressException("wrong type");
	public static final AddressException INVALID_HEX = new AddressException("wrong HEX format");

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

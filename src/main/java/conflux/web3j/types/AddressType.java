package conflux.web3j.types;

import java.util.HashMap;
import java.util.Optional;

import org.web3j.utils.Numeric;

public enum AddressType {
	Null("null", "null address type required"),
	Builtin("builtin", "builtin address type required"),
	User("user", "user address type required"),
	Contract("contract", "contract address type required"),
    Unknown("unknown", "unknown address type required");

	private static final HashMap<String, Character> TYPE_MAP = new HashMap<>();
	static {
		TYPE_MAP.put("builtin", '0');
		TYPE_MAP.put("user", '1');
		TYPE_MAP.put("contract", '8');
	}

	private String value;
	private String typeMismatchException;

	private AddressType(String value, String ae) {
		this.value = value;
		this.typeMismatchException = ae;
	}

	public String getValue() {
		return this.value;
	}

	public AddressException getTypeMismatchException() {
		return new AddressException(typeMismatchException);
	}
	
	public String normalize(String hexAddress) {
		return String.format("0x%s%s", TYPE_MAP.get(this.value), Numeric.cleanHexPrefix(hexAddress).substring(1));
	}

	public static Optional<AddressType> parse(char ch) {
		switch (ch) {
			case '0':
				return Optional.of(Builtin);
			case '1':
				return Optional.of(User);
			case '8':
				return Optional.of(Contract);
			default:
				return Optional.of(Unknown);
		}
//		return Optional.empty();
	}

	private static final int HEX_LENGTH_WITH_PREFIX = 42;

	public static void validateHexAddress(String hexValue) throws AddressException {
		validateHexAddress(hexValue, null);
	}

	public static void validateHexAddress(String hexValue, AddressType expectedType) throws AddressException {
		if (!Numeric.containsHexPrefix(hexValue)) {
			throw new AddressException(AddressException.INVALID_PREFIX);
		}

		if (hexValue.length() != HEX_LENGTH_WITH_PREFIX) {
			throw new AddressException(AddressException.INVALID_LENGTH);
		}

		Optional<AddressType> type = AddressType.parse(hexValue.charAt(2));
		if (!type.isPresent()) {
			throw new AddressException(AddressException.INVALID_TYPE);
		}

		if (expectedType != null && !type.get().equals(expectedType)) {
			throw expectedType.getTypeMismatchException();
		}

		for (int i = 2; i < HEX_LENGTH_WITH_PREFIX; i++) {
			char ch = hexValue.charAt(i);
			if (ch < '0' || (ch > '9' && ch < 'A') || (ch > 'Z' && ch < 'a') || ch > 'z') {
				throw new AddressException(AddressException.INVALID_HEX);
			}
		}
	}
}

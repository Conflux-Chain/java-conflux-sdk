package conflux.web3j.types;

import java.util.HashMap;
import java.util.Optional;

import org.web3j.utils.Numeric;

public enum AddressType {
	Null("null", new AddressException("null address type required")),
	Builtin("builtin", new AddressException("builtin address type required")),
	User("user", new AddressException("user address type required")),
	Contract("contract", new AddressException("contract address type required"));

	private static final HashMap<String, Character> TYPE_MAP = new HashMap<>();
	static {
		TYPE_MAP.put("builtin", '0');
		TYPE_MAP.put("user", '1');
		TYPE_MAP.put("contract", '8');
	}

	private String value;
	private AddressException typeMismatchException;

	private AddressType(String value, AddressException ae) {
		this.value = value;
		this.typeMismatchException = ae;
	}

	public String getValue() {
		return this.value;
	}

	public AddressException getTypeMismatchException() {
		return typeMismatchException;
	}
	
	public String normalize(String hexAddress) {
		return String.format("0x%s%s", TYPE_MAP.get(this.value), Numeric.cleanHexPrefix(hexAddress).substring(1));
	}

	public static Optional<AddressType> parse(char ch) {
		switch (ch) {
			case '0':
				Optional.of(Builtin);
			case '1':
				Optional.of(User);
			case '8':
				Optional.of(Contract);
		}
		return Optional.empty();
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

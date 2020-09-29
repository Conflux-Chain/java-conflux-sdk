package conflux.web3j.types;

import java.util.Optional;

import org.web3j.utils.Numeric;

public class Address {
	
	private static final int HEX_LENGTH_WITH_PREFIX = 42;
	
	public static void validate(String hexValue) throws AddressException {
		validate(hexValue, null);
	}
	
	public static void validate(String hexValue, AddressType expectedType) throws AddressException {
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

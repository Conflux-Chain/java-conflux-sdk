package conflux.web3j.types;

import java.util.Optional;

import org.web3j.utils.Numeric;

public enum AddressType {
	User('1', new AddressException("user address type required")),
	Contract('8', new AddressException("contract address type required"));

	private char value;
	private AddressException typeMismatchException;

	private AddressType(char value, AddressException ae) {
		this.value = value;
		this.typeMismatchException = ae;
	}

	public char getValue() {
		return this.value;
	}

	public AddressException getTypeMismatchException() {
		return typeMismatchException;
	}
	
	public String normalize(String address) {
		return String.format("0x%s%s", this.value, Numeric.cleanHexPrefix(address).substring(1));
	}

	public static Optional<AddressType> parse(char ch) {
		for (AddressType type : AddressType.values()) {
			if (type.value == ch) {
				return Optional.of(type);
			}
		}

		return Optional.empty();
	}

}

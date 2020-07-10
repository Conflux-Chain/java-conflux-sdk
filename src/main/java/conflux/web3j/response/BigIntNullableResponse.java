package conflux.web3j.response;

import java.math.BigInteger;
import java.util.Optional;

import org.web3j.protocol.core.Response;
import org.web3j.utils.Numeric;

import conflux.web3j.HasValue;

public class BigIntNullableResponse extends Response<String> implements HasValue<Optional<BigInteger>> {

	@Override
	public Optional<BigInteger> getValue() {
		String hexEncoded = this.getResult();
		
		if (hexEncoded == null) {
			return Optional.empty();
		} else {
			return Optional.of(Numeric.decodeQuantity(hexEncoded));
		}
	}

}

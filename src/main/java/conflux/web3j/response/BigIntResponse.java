package conflux.web3j.response;

import java.math.BigInteger;

import org.web3j.protocol.core.Response;
import org.web3j.utils.Numeric;

import conflux.web3j.HasValue;

public class BigIntResponse extends Response<String> implements HasValue<BigInteger> {

	@Override
	public BigInteger getValue() {
		String hexEncoded = this.getResult();
		return Numeric.decodeQuantity(hexEncoded);
	}

}

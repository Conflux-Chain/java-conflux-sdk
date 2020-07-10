package conflux.web3j.response;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

import org.web3j.protocol.core.Response;
import org.web3j.utils.Numeric;

import conflux.web3j.HasValue;

public class BlockRevertRateResponse extends Response<String> implements HasValue<BigDecimal> {
	
	private static final BigDecimal BASE = new BigDecimal(BigInteger.valueOf(2).pow(256));
	
	public static int defaultScale = 7;

	@Override
	public BigDecimal getValue() {
		String hexValue = this.getResult();
		if (hexValue == null) {
			return BigDecimal.ONE;
		}
		
		BigInteger risk = Numeric.decodeQuantity(hexValue);
		
		return new BigDecimal(risk).divide(BASE, defaultScale, RoundingMode.UP);
	}

}

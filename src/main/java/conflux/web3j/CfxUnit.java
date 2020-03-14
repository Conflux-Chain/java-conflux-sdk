package conflux.web3j;

import java.math.BigDecimal;
import java.math.BigInteger;

public class CfxUnit {
	
	private static final BigInteger DRIPS_PER_CFX_INT = BigInteger.TEN.pow(18);
	private static final BigDecimal DRIPS_PER_CFX_DECIMAL = new BigDecimal(DRIPS_PER_CFX_INT);
	private static final BigInteger DRIPS_PER_GDRIP = BigInteger.TEN.pow(9);
	
	public static final BigInteger GDRIP_ONE = BigInteger.TEN.pow(9);
	public static final BigInteger GDRIP_TEN = BigInteger.TEN.pow(10);
	public static final BigInteger CFX_ONE = BigInteger.TEN.pow(18);
	
	public static final BigInteger DEFAULT_GAS_PRICE = GDRIP_TEN;
	public static final BigInteger DEFAULT_GAS_LIMIT = BigInteger.valueOf(21000);
	
	public static BigInteger cfx2Drip(long cfx) {
		return BigInteger.valueOf(cfx).multiply(DRIPS_PER_CFX_INT);
	}
	
	public static BigInteger cfx2Drip(double cfx) {
		return BigDecimal.valueOf(cfx).multiply(DRIPS_PER_CFX_DECIMAL).toBigIntegerExact();
	}
	
	public static BigInteger gdrip2Drip(long gdrip) {
		return BigInteger.valueOf(gdrip).multiply(DRIPS_PER_GDRIP);
	}
	
	public static BigDecimal drip2Cfx(BigInteger drip) {
		return new BigDecimal(drip).divide(DRIPS_PER_CFX_DECIMAL);
	}

}

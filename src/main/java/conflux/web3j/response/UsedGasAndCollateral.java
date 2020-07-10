package conflux.web3j.response;

import java.math.BigInteger;

import org.web3j.utils.Numeric;

public class UsedGasAndCollateral {
	
	public static class Response extends CfxResponse<UsedGasAndCollateral> {}
	
	private String gasUsed;
	private String storageCollateralized;
	
	public BigInteger getGasUsed() {
		return Numeric.decodeQuantity(this.gasUsed);
	}
	
	public void setGasUsed(String gasUsed) {
		this.gasUsed = gasUsed;
	}
	
	public BigInteger getStorageCollateralized() {
		return Numeric.decodeQuantity(this.storageCollateralized);
	}
	
	public void setStorageCollateralized(String storageCollateralized) {
		this.storageCollateralized = storageCollateralized;
	}
	
	@Override
	public String toString() {
		return String.format("{gasUsed = %s, storageCollateralized = %s}", this.getGasUsed(), this.getStorageCollateralized());
	}
}

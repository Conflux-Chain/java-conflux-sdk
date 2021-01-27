package conflux.web3j.response;

import java.math.BigInteger;

import conflux.web3j.types.Address;
import org.web3j.utils.Numeric;

public class SponsorInfo {
	
	public static class Response extends CfxResponse<SponsorInfo> {}
	
    private Address sponsorForGas;
    private Address sponsorForCollateral;
    private String sponsorGasBound;
    private String sponsorBalanceForGas;
    private String sponsorBalanceForCollateral;
    
	public Address getSponsorForGas() {
		return sponsorForGas;
	}
	
	public void setSponsorForGas(Address sponsorForGas) {
		this.sponsorForGas = sponsorForGas;
	}
	
	public Address getSponsorForCollateral() {
		return sponsorForCollateral;
	}
	
	public void setSponsorForCollateral(Address sponsorForCollateral) {
		this.sponsorForCollateral = sponsorForCollateral;
	}
	
	public BigInteger getSponsorGasBound() {
		return Numeric.decodeQuantity(this.sponsorGasBound);
	}
	
	public void setSponsorGasBound(String sponsorGasBound) {
		this.sponsorGasBound = sponsorGasBound;
	}
	
	public BigInteger getSponsorBalanceForGas() {
		return Numeric.decodeQuantity(this.sponsorBalanceForGas);
	}
	
	public void setSponsorBalanceForGas(String sponsorBalanceForGas) {
		this.sponsorBalanceForGas = sponsorBalanceForGas;
	}
	
	public BigInteger getSponsorBalanceForCollateral() {
		return Numeric.decodeQuantity(this.sponsorBalanceForCollateral);
	}
	
	public void setSponsorBalanceForCollateral(String sponsorBalanceForCollateral) {
		this.sponsorBalanceForCollateral = sponsorBalanceForCollateral;
	}

}

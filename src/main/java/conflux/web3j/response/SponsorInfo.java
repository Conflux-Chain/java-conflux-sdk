package conflux.web3j.response;

import java.math.BigInteger;

import org.web3j.utils.Numeric;

public class SponsorInfo {
	
	public static class Response extends CfxResponse<SponsorInfo> {}
	
    private String sponsorForGas;
    private String sponsorForCollateral;
    private String sponsorGasBound;
    private String sponsorBalanceForGas;
    private String sponsorBalanceForCollateral;
    
	public String getSponsorForGas() {
		return sponsorForGas;
	}
	
	public void setSponsorForGas(String sponsorForGas) {
		this.sponsorForGas = sponsorForGas;
	}
	
	public String getSponsorForCollateral() {
		return sponsorForCollateral;
	}
	
	public void setSponsorForCollateral(String sponsorForCollateral) {
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

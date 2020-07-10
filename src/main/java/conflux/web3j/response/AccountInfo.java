package conflux.web3j.response;

import java.math.BigInteger;

import org.web3j.utils.Numeric;

public class AccountInfo {
	
	public static class Response extends CfxResponse<AccountInfo> {}
	
    private String balance;
    private String nonce;
    private String codeHash;
    private String stakingBalance;
    private String collateralForStorage;
    private String accumulatedInterestReturn;
    private String admin;
    
	public BigInteger getBalance() {
		return Numeric.decodeQuantity(this.balance);
	}
	
	public void setBalance(String balance) {
		this.balance = balance;
	}
	
	public BigInteger getNonce() {
		return Numeric.decodeQuantity(this.nonce);
	}
	
	public void setNonce(String nonce) {
		this.nonce = nonce;
	}
	
	public String getCodeHash() {
		return codeHash;
	}
	
	public void setCodeHash(String codeHash) {
		this.codeHash = codeHash;
	}
	
	public BigInteger getStakingBalance() {
		return Numeric.decodeQuantity(this.stakingBalance);
	}
	
	public void setStakingBalance(String stakingBalance) {
		this.stakingBalance = stakingBalance;
	}
	
	public BigInteger getCollateralForStorage() {
		return Numeric.decodeQuantity(this.collateralForStorage);
	}
	
	public void setCollateralForStorage(String collateralForStorage) {
		this.collateralForStorage = collateralForStorage;
	}
	
	public BigInteger getAccumulatedInterestReturn() {
		return Numeric.decodeQuantity(this.accumulatedInterestReturn);
	}
	
	public void setAccumulatedInterestReturn(String accumulatedInterestReturn) {
		this.accumulatedInterestReturn = accumulatedInterestReturn;
	}
	
	public String getAdmin() {
		return admin;
	}
	
	public void setAdmin(String admin) {
		this.admin = admin;
	}

}

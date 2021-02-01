package conflux.web3j.response;

import java.math.BigInteger;

import conflux.web3j.types.Address;
import org.web3j.utils.Numeric;

public class AccountInfo {
	
	public static class Response extends CfxResponse<AccountInfo> {}

	private Address address;
    private String balance;
    private String nonce;
    private String codeHash;
    private String stakingBalance;
    private String collateralForStorage;
    private String accumulatedInterestReturn;
    private Address admin;

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
    
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
	
	public Address getAdmin() {
		return admin;
	}
	
	public void setAdmin(Address admin) {
		this.admin = admin;
	}
}

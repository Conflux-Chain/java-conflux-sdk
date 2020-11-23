package conflux.web3j.response;

import org.web3j.utils.Numeric;

import java.math.BigInteger;

public class StorageChange {
//    public static class Response extends CfxResponse<StorageChange> {}

    private String address;
    private String collaterals;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigInteger getCollaterals () {
        return Numeric.decodeQuantity(collaterals);
    }

    public void setCollaterals(String collaterals) {
        this.collaterals = collaterals;
    }
}

package conflux.web3j.response;

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

    public String getCollaterals () {
        return collaterals;
    }

    public void setCollaterals(String collaterals) {
        this.collaterals = collaterals;
    }
}

package conflux.web3j.response;

import conflux.web3j.types.Address;
import org.web3j.utils.Numeric;

import java.math.BigInteger;

public class StorageChange {
//    public static class Response extends CfxResponse<StorageChange> {}

    private Address address;
    private String collaterals;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public BigInteger getCollaterals () {
        return Numeric.decodeQuantity(collaterals);
    }

    public void setCollaterals(String collaterals) {
        this.collaterals = collaterals;
    }
}

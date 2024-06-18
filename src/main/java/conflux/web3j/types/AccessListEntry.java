package conflux.web3j.types;
import java.util.List;

public class AccessListEntry {

    private Address address;
    private List<String> storageKeys;

    public List<String> getStorageKeys() {
        return storageKeys;
    }

    public void setStorageKeys(List<String> storageKeys) {
        this.storageKeys = storageKeys;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(CfxAddress address) {
        this.address = address;
    }
}

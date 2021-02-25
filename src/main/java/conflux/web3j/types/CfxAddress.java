package conflux.web3j.types;

/*
* CfxAddress is an alias to class Address
* To easily distinguish from org.web3j.abi.datatypes.Address;
* */
public class CfxAddress extends Address {
    public CfxAddress(String address) throws AddressException {
        super(address);
    }

    public CfxAddress(String hexAddress, int netId) throws AddressException {
        super(hexAddress, netId);
    }

    public CfxAddress(byte[] addressBuffer, int netId) throws AddressException {
        super(addressBuffer, netId);
    }
}

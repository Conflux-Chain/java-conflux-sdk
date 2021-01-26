package conflux.web3j.types;

import com.google.common.io.BaseEncoding;
import com.google.common.primitives.Bytes;
import conflux.web3j.crypto.ConfluxBase32;
import org.web3j.abi.datatypes.Type;

import java.util.Arrays;

public class Address implements Type<String> {
    private final String address;  // base32Check address
    private final String hexAddress;
    private final int netId;
    private final AddressType addressType;

    /*
    * @param address {string} Pass a new Conflux address
    * */
    public Address(String address) throws AddressException {
        String hexAddress = Address.decode(address);
        this.address = address;
        this.hexAddress = hexAddress;
        this.addressType = Address.addressType(hexAddress);
        this.netId = Address.decodeNetId(address.split(Address.DELIMITER)[0]);
    }

    public Address(String hexAddress, int netId) throws AddressException {
        this.address = Address.encode(hexAddress, netId);
        this.addressType = Address.addressType(hexAddress);
        this.netId = netId;
        this.hexAddress = hexAddress;
    }

    public Address(byte[] addressBuffer, int netId) throws AddressException {
        this.address = Address.encode(addressBuffer, netId);
        this.addressType = Address.addressType(addressBuffer);
        this.netId = netId;
        this.hexAddress = "0x" + BaseEncoding.base16().encode(addressBuffer).toLowerCase();
    }

    public String getAddress() {
        return address;
    }

    public int getNetworkId() {
        return netId;
    }

    public AddressType getType() {
        return addressType;
    }

    public String getHexAddress() {
        return hexAddress;
    }

    public String toString() {
        String formatStr = "{netId = %d, hexAddress = %s, address = %s}";
        return String.format(formatStr, this.netId, this.hexAddress, this.address);
    }

    public String getValue() {
        return hexAddress;
    }

    public String getTypeAsString() {
        return "address";
    }

    // CIP37 specification: https://github.com/Conflux-Chain/CIPs/blob/master/CIPs/cip-37.md
    // static methods used to convert from hex to cfx address and vice
    public static final int NETWORK_ID_MAINNET = 1029;
    public static final int NETWORK_ID_TESTNET = 1;

    public static final String NETWORK_MAIN = "cfx";
    public static final String NETWORK_TEST = "cfxtest";
    public static final String NETWORK_LOCAL_PREFIX = "net";

    public static final byte[] ADDRESS_NULL = "0x0000000000000000000000000000000000000000".getBytes();

    private static final byte VERSION_BYTE = 0x00;
    private static final int CHECKSUM_LEN = 8;
    private static final int HEX_BUFFER_LEN = 20;
    private static final int HEX_PREFIX_LEN = 2;
    private static final String HEX_PREFIX = "0X";
    private static final String DELIMITER = ":";
    private static final byte[] CHECKSUM_TEMPLATE = new byte[]{0, 0, 0, 0, 0, 0, 0, 0};
    private static final long NET_ID_LIMIT = 4294967295L;  // 0xFFFFFFFF
    private static final int CFX_ADDRESS_CHAR_LENGTH = 42;

    public static String encode(byte[] hexBuf, int netId) throws AddressException {
        if(hexBuf == null || hexBuf.length != HEX_BUFFER_LEN) {
            throw new AddressException("hexBuf is null or length is not 20");
        }
        StringBuilder strBuilder = new StringBuilder();
        String chainPrefix = encodeNetId(netId);
        String payload = ConfluxBase32.encode(encodePayload(hexBuf));
        strBuilder.append(chainPrefix);
        strBuilder.append(DELIMITER);
        strBuilder.append(payload);
        strBuilder.append(createCheckSum(chainPrefix, payload));
        return strBuilder.toString();
    }

    public static String encode(String hexAddress, int netId) throws AddressException {
        if(hexAddress == null) {
            throw new AddressException("Invalid argument");
        }
        return encode(addressBufferFromHex(hexAddress), netId);
    }

    public static String encode(String hexString, int chainId, boolean verbose)  throws AddressException {
        String shortAddress = encode(hexString, chainId);
        if (!verbose) {
            return shortAddress;
        }
        String[] parts = shortAddress.split(DELIMITER);
        String typeStr = "type." + addressType(hexString);
        return String.join(DELIMITER, parts[0], typeStr , parts[1]).toUpperCase();
    }

    public static String decode(String cfxAddress) throws AddressException {
        if(cfxAddress == null || !haveNetworkPrefix(cfxAddress)) {
            throw new AddressException("Invalid argument");
        }
        cfxAddress = cfxAddress.toLowerCase();
        String[] parts = cfxAddress.split(DELIMITER);
        if (parts.length < 2) {
            throw new AddressException("Address should have at least two part");
        }
        String network = parts[0];
        String payloadWithSum = parts[parts.length-1];
        if (!ConfluxBase32.isValid(payloadWithSum)) {
            throw new AddressException("Input contain invalid base32 chars");
        }
        if (payloadWithSum.length() != CFX_ADDRESS_CHAR_LENGTH) {
            throw new AddressException("Address payload should have 42 chars");
        }
        String sum = payloadWithSum.substring(payloadWithSum.length()-CHECKSUM_LEN);
        String payload = payloadWithSum.substring(0, payloadWithSum.length()-CHECKSUM_LEN);
        if (!sum.equals(createCheckSum(network, payload))) {
            throw new AddressException("Invalid checksum");
        }
        byte[] raw = ConfluxBase32.decode(payload);
        String hexAddress = HEX_PREFIX + BaseEncoding.base16().encode(raw).substring(HEX_PREFIX_LEN);
        return hexAddress.toLowerCase();
    }

    public static boolean isValid(String cfxAddressStr) {
        try {
            Address.decode(cfxAddressStr);
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    public static boolean haveNetworkPrefix(String cfxAddressStr) {
        cfxAddressStr = cfxAddressStr.toLowerCase();
        return cfxAddressStr != null && (cfxAddressStr.startsWith(NETWORK_MAIN) || cfxAddressStr.startsWith(NETWORK_TEST) || cfxAddressStr.startsWith(NETWORK_LOCAL_PREFIX));
    }

    public static String normalizeBase32Address(String address, int netId) throws Exception {
        if(isValid(address)) {
            return address;
        }
        return encode(address, netId);
    }

    public static String normalizeHexAddress(String address) throws AddressException {
        if(address == null) {
            throw new AddressException("Invalid argument");
        }
        if(haveNetworkPrefix(address)) {
            return decode(address);
        }
        return address;
    }

    private static byte[] encodePayload(byte[] addressBuf) {
        return Bytes.concat(new byte[]{VERSION_BYTE}, addressBuf);
    }

    private static byte[] decodePayload(byte[] payload) throws AddressException {
        if(payload.length <= 1 || payload[0] != VERSION_BYTE) {
            throw new AddressException("Can not recognize version byte");
        }
        return Arrays.copyOfRange(payload, 1, payload.length);
    }

    private static byte[] addressBufferFromHex(String hexAddress) throws AddressException {
        hexAddress = hexAddress.toUpperCase();
        if (hexAddress.startsWith(HEX_PREFIX)) {
            hexAddress = hexAddress.substring(HEX_PREFIX_LEN);
        }
        byte[] buf = BaseEncoding.base16().decode(hexAddress);
        if (buf.length != HEX_BUFFER_LEN) {
            throw new AddressException("hex buffer length should be 20");
        }
        return buf;
    }

    private static byte[] prefixToWords(String prefix) {
        byte[] result = prefix.getBytes();
        for(int i = 0; i < result.length; i++) {
            result[i] = (byte)(result[i] & 0x1f);
        }
        return result;
    }

    private static String createCheckSum(String chainPrefix, String payload) throws AddressException {
        byte[] prefixBuf = prefixToWords(chainPrefix);
        byte[] delimiterBuf = new byte[]{0};  // use 0
        byte[] payloadBuf = ConfluxBase32.decodeWords(payload);
        long n = polyMod(Bytes.concat(prefixBuf, delimiterBuf, payloadBuf, CHECKSUM_TEMPLATE));
        return ConfluxBase32.encode(checksumBytes(n));
    }

    private static String encodeNetId(int netId) throws AddressException {
        if(netId <= 0) {
            throw new AddressException("chainId should be passed as in range [1, 0xFFFFFFFF]");
        }
        switch (netId) {
            case NETWORK_ID_MAINNET:
                return NETWORK_MAIN;
            case NETWORK_ID_TESTNET:
                return NETWORK_TEST;
            default:
                return NETWORK_LOCAL_PREFIX + netId;
        }
    }

    private static int decodeNetId(String prefix) throws AddressException {
        prefix = prefix.toLowerCase();
        switch (prefix) {
            case NETWORK_MAIN:
                return NETWORK_ID_MAINNET;
            case NETWORK_TEST:
                return NETWORK_ID_TESTNET;
            default:
                if(!prefix.startsWith(NETWORK_LOCAL_PREFIX)) {
                    throw new AddressException("netId prefix should be passed by 'cfx', 'cfxtest' or 'net[n]'");
                }
                int netId = Integer.parseInt(prefix.substring(3));
                if(netId == 1 || netId == 1029) {
                    throw new AddressException("net1 or net1029 are invalid");
                }
                return netId;
        }
    }

    private static AddressType addressType(String hexAddress) throws AddressException {
        byte[] buf = addressBufferFromHex(hexAddress);
        return addressType(buf);
    }

    private static AddressType addressType(byte[] addressBuffer) throws AddressException {
        if (Arrays.equals(addressBuffer, ADDRESS_NULL)) return AddressType.Null;
        switch (addressBuffer[0] & 0xf0) {
            case 0x00:
                return AddressType.Builtin;
            case 0x10:
                return AddressType.User;
            case 0x80:
                return AddressType.Contract;
            default:
                throw new AddressException(AddressException.INVALID_TYPE);
        }
    }

    private static byte[] checksumBytes(long data) {
        return new byte[]{
                (byte) ((data >> 32) & 0xff),
                (byte) ((data >> 24) & 0xff),
                (byte) ((data >> 16) & 0xff),
                (byte) ((data >> 8) & 0xff),
                (byte) ((data) & 0xff),
        };
    }

    private static long polyMod(byte[] data) {
        long c = 1L;
        for (byte datum : data) {
            byte c0 = (byte) (c >> 35);
            c = ((c & Long.decode("0x07ffffffff")) << 5) ^ datum;
            if ((c0 & 0x01) != 0) c ^= Long.decode("0x98f2bc8e61");
            if ((c0 & 0x02) != 0) c ^= Long.decode("0x79b76d99e2");
            if ((c0 & 0x04) != 0) c ^= Long.decode("0xf33e5fb3c4");
            if ((c0 & 0x08) != 0) c ^= Long.decode("0xae2eabe2a8");
            if ((c0 & 0x10) != 0) c ^= Long.decode("0x1e4f43e470");
        }
        return c ^ 1;
    }
}

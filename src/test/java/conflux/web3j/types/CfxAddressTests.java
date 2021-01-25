package conflux.web3j.types;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CfxAddressTests {
    @Test
    @DisplayName("Conflux Address Encode")
    void cfxAddressEncoderTest() throws Exception {
        assertEquals("cfx:aajg4wt2mbmbb44sp6szd783ry0jtad5bea80xdy7p", CfxAddress.encode("106d49f8505410eb4e671d51f7d96d2c87807b09", 1029), "");
        assertEquals("CFX:TYPE.USER:AAJG4WT2MBMBB44SP6SZD783RY0JTAD5BEA80XDY7P", CfxAddress.encode("106d49f8505410eb4e671d51f7d96d2c87807b09", 1029, true), "");
        assertEquals("cfx:acag4wt2mbmbb44sp6szd783ry0jtad5bex25t8vc9", CfxAddress.encode("806d49f8505410eb4e671d51f7d96d2c87807b09", 1029), "");
        assertEquals("CFX:TYPE.CONTRACT:ACAG4WT2MBMBB44SP6SZD783RY0JTAD5BEX25T8VC9", CfxAddress.encode("806d49f8505410eb4e671d51f7d96d2c87807b09", 1029, true), "");
        assertEquals("cfx:aaag4wt2mbmbb44sp6szd783ry0jtad5beaar3k429", CfxAddress.encode("006d49f8505410eb4e671d51f7d96d2c87807b09", 1029), "");
        assertEquals("CFX:TYPE.BUILTIN:AAAG4WT2MBMBB44SP6SZD783RY0JTAD5BEAAR3K429", CfxAddress.encode("006d49f8505410eb4e671d51f7d96d2c87807b09", 1029, true), "");

        assertEquals("cfx:aajg4wt2mbmbb44sp6szd783ry0jtad5bea80xdy7p", CfxAddress.encode("106d49f8505410eb4e671d51f7d96d2c87807b09", 1029, false), "");
        assertEquals("cfxtest:acag4wt2mbmbb44sp6szd783ry0jtad5be3xj925gz", CfxAddress.encode("806d49f8505410eb4e671d51f7d96d2c87807b09", 1, false), "");
        assertEquals("net10086:aaag4wt2mbmbb44sp6szd783ry0jtad5benr1ap5gp", CfxAddress.encode("006d49f8505410eb4e671d51f7d96d2c87807b09", 10086, false), "");

        assertEquals("cfx:acc7uawf5ubtnmezvhu9dhc6sghea0403y2dgpyfjp", CfxAddress.encode("0x85d80245dc02f5a89589e1f19c5c718e405b56cd", 1029, false), "");
        assertEquals("cfxtest:acc7uawf5ubtnmezvhu9dhc6sghea0403ywjz6wtpg", CfxAddress.encode("0x85d80245dc02f5a89589e1f19c5c718e405b56cd", 1, false), "");
        assertEquals("CFXTEST:TYPE.CONTRACT:ACC7UAWF5UBTNMEZVHU9DHC6SGHEA0403YWJZ6WTPG", CfxAddress.encode("0x85d80245dc02f5a89589e1f19c5c718e405b56cd", 1, true), "");

        assertEquals("cfx:aarc9abycue0hhzgyrr53m6cxedgccrmmyybjgh4xg", CfxAddress.encode("0x1a2f80341409639ea6a35bbcab8299066109aa55", 1029, false), "");
        assertEquals("cfxtest:aarc9abycue0hhzgyrr53m6cxedgccrmmy8m50bu1p", CfxAddress.encode("0x1a2f80341409639ea6a35bbcab8299066109aa55", 1, false), "");
        assertEquals("CFXTEST:TYPE.USER:AARC9ABYCUE0HHZGYRR53M6CXEDGCCRMMY8M50BU1P", CfxAddress.encode("0x1a2f80341409639ea6a35bbcab8299066109aa55", 1, true), "");

        assertEquals("cfx:aaejuaaaaaaaaaaaaaaaaaaaaaaaaaaaajrwuc9jnb", CfxAddress.encode("0x0888000000000000000000000000000000000002", 1029, false), "");
        assertEquals("cfxtest:aaejuaaaaaaaaaaaaaaaaaaaaaaaaaaaajh3dw3ctn", CfxAddress.encode("0x0888000000000000000000000000000000000002", 1, false), "");
        assertEquals("CFXTEST:TYPE.BUILTIN:AAEJUAAAAAAAAAAAAAAAAAAAAAAAAAAAAJH3DW3CTN", CfxAddress.encode("0x0888000000000000000000000000000000000002", 1, true), "");
        assertEquals("0x0888000000000000000000000000000000000002", CfxAddress.decode("CFXTEST:TYPE.BUILTIN:AAEJUAAAAAAAAAAAAAAAAAAAAAAAAAAAAJH3DW3CTN"), "");
        assertEquals("0x0888000000000000000000000000000000000002", CfxAddress.decode("cfxtest:aaejuaaaaaaaaaaaaaaaaaaaaaaaaaaaajh3dw3ctn"), "");
        assertEquals("0x0888000000000000000000000000000000000002", CfxAddress.decode("cfxtest:aaejuaaaaaaaaaaaaaaaaaaaaaaaaaaaajh3dw3ctn"), "");

        assertEquals("0x1a2f80341409639ea6a35bbcab8299066109aa55", CfxAddress.decode("cfx:aarc9abycue0hhzgyrr53m6cxedgccrmmyybjgh4xg"), "");
        assertEquals("0x85d80245dc02f5a89589e1f19c5c718e405b56cd", CfxAddress.decode("cfxtest:acc7uawf5ubtnmezvhu9dhc6sghea0403ywjz6wtpg"), "");
//
//        assertEquals("", CfxAddress.encode("", 10086, false), "");
//        assertEquals("", CfxAddress.encode("", 10086, false), "");
//        assertEquals("", CfxAddress.encode("", 10086, false), "");
    }

    @Test
    @DisplayName("Address Corner Case")
    void cfxAddressEncoderFailTest() throws Exception {
        // encode: null, invalid hex char, invalid hex length, invalid chainId
        Assertions.assertThrows(Exception.class, () -> {
            CfxAddress.encode("zzzz", 1);
        });
        Assertions.assertThrows(AddressException.class, () -> {
            CfxAddress.encode((byte[]) null, 1);
        });
        Assertions.assertThrows(AddressException.class, () -> {
            CfxAddress.encode((String) null, 1);
        });
        Assertions.assertThrows(AddressException.class, () -> {
            CfxAddress.encode("invalid len".getBytes(), 1);
        });
//
//        // decode: null, without :, invalid base32, invalid length, invalid checksum
        Assertions.assertThrows(AddressException.class, () -> {
            CfxAddress.decode(null);
        });
        Assertions.assertThrows(AddressException.class, () -> {
            CfxAddress.decode("012345");
        });
        Assertions.assertThrows(AddressException.class, () -> {
            CfxAddress.decode("cfx:0206ujfsa1a11uuecwen3xytdmp8f03v14ksvfy");
        });
        Assertions.assertThrows(AddressException.class, () -> {
            CfxAddress.decode("cfx:0206ujfsa1a11uuecwen3xytdmp8f03v14ksvfyl");
        });
        Assertions.assertThrows(AddressException.class, () -> {
            CfxAddress.decode("cfx:0206ujfsa1a");
        });
        Assertions.assertThrows(AddressException.class, () -> {
            CfxAddress.decode("cfx:0048g000000000000000000000000000087t3jt2fd");
        });
    }
}

package conflux.web3j.types;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CfxAddressTests {
    @Test
    @DisplayName("Conflux Address Encode")
    void cfxAddressEncoderTest() throws Exception {
        assertEquals("cfx:0086ujfsa1a11uuecwen3xytdmp8f03v140ypk3mxc", CfxAddress.encode("106d49f8505410eb4e671d51f7d96d2c87807b09", 1029), "");
        assertEquals("CFX:TYPE.USER:0086UJFSA1A11UUECWEN3XYTDMP8F03V140YPK3MXC", CfxAddress.encode("106d49f8505410eb4e671d51f7d96d2c87807b09", 1029, true), "");
        assertEquals("cfx:0206ujfsa1a11uuecwen3xytdmp8f03v14ksvfyh2z", CfxAddress.encode("806d49f8505410eb4e671d51f7d96d2c87807b09", 1029), "");
        assertEquals("CFX:TYPE.CONTRACT:0206UJFSA1A11UUECWEN3XYTDMP8F03V14KSVFYH2Z", CfxAddress.encode("806d49f8505410eb4e671d51f7d96d2c87807b09", 1029, true), "");
        assertEquals("cfx:0006ujfsa1a11uuecwen3xytdmp8f03v1400dt9usz", CfxAddress.encode("006d49f8505410eb4e671d51f7d96d2c87807b09", 1029), "");
        assertEquals("CFX:TYPE.BUILTIN:0006UJFSA1A11UUECWEN3XYTDMP8F03V1400DT9USZ", CfxAddress.encode("006d49f8505410eb4e671d51f7d96d2c87807b09", 1029, true), "");

        assertEquals("cfx:0086ujfsa1a11uuecwen3xytdmp8f03v140ypk3mxc", CfxAddress.encode("106d49f8505410eb4e671d51f7d96d2c87807b09", 1029, false), "");
        assertEquals("cfxtest:0206ujfsa1a11uuecwen3xytdmp8f03v14tk8zsv6n", CfxAddress.encode("806d49f8505410eb4e671d51f7d96d2c87807b09", 1, false), "");
        assertEquals("net10086:0006ujfsa1a11uuecwen3xytdmp8f03v14bdr0cv6c", CfxAddress.encode("006d49f8505410eb4e671d51f7d96d2c87807b09", 10086, false), "");

        assertEquals("cfx:022xg0j5vg1fba4nh7gz372we6740puptms36cm58c", CfxAddress.encode("0x85d80245dc02f5a89589e1f19c5c718e405b56cd", 1029, false), "");
        assertEquals("cfxtest:022xg0j5vg1fba4nh7gz372we6740puptmj8nwjfc6", CfxAddress.encode("0x85d80245dc02f5a89589e1f19c5c718e405b56cd", 1, false), "");
        assertEquals("CFXTEST:TYPE.CONTRACT:022XG0J5VG1FBA4NH7GZ372WE6740PUPTMJ8NWJFC6", CfxAddress.encode("0x85d80245dc02f5a89589e1f19c5c718e405b56cd", 1, true), "");

        assertEquals("cfx:00d2z01m2g4p77n6mddvtaw2k43622daamm1867uk6", CfxAddress.encode("0x1a2f80341409639ea6a35bbcab8299066109aa55", 1029, false), "");
        assertEquals("cfxtest:00d2z01m2g4p77n6mddvtaw2k43622daamyavp1grc", CfxAddress.encode("0x1a2f80341409639ea6a35bbcab8299066109aa55", 1, false), "");
        assertEquals("CFXTEST:TYPE.USER:00D2Z01M2G4P77N6MDDVTAW2K43622DAAMYAVP1GRC", CfxAddress.encode("0x1a2f80341409639ea6a35bbcab8299066109aa55", 1, true), "");

        assertEquals("cfx:0048g00000000000000000000000000008djg2z8b1", CfxAddress.encode("0x0888000000000000000000000000000000000002", 1029, false), "");
        assertEquals("cfxtest:0048g000000000000000000000000000087t3jt2fb", CfxAddress.encode("0x0888000000000000000000000000000000000002", 1, false), "");
        assertEquals("CFXTEST:TYPE.BUILTIN:0048G000000000000000000000000000087T3JT2FB", CfxAddress.encode("0x0888000000000000000000000000000000000002", 1, true), "");
        assertEquals("0x0888000000000000000000000000000000000002", CfxAddress.decode("CFXTEST:TYPE.BUILTIN:0048G000000000000000000000000000087T3JT2FB"), "");
        assertEquals("0x0888000000000000000000000000000000000002", CfxAddress.decode("cfxtest:0048g000000000000000000000000000087t3jt2fb"), "");
        assertEquals("0x0888000000000000000000000000000000000002", CfxAddress.decode("cfx:0048g00000000000000000000000000008djg2z8b1"), "");

        assertEquals("0x1a2f80341409639ea6a35bbcab8299066109aa55", CfxAddress.decode("cfx:00d2z01m2g4p77n6mddvtaw2k43622daamm1867uk6"), "");
        assertEquals("0x85d80245dc02f5a89589e1f19c5c718e405b56cd", CfxAddress.decode("cfx:022xg0j5vg1fba4nh7gz372we6740puptms36cm58c"), "");
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
        Assertions.assertThrows(Exception.class, () -> {
            CfxAddress.encode((byte[]) null, 1);
        });
        Assertions.assertThrows(Exception.class, () -> {
            CfxAddress.encode((String) null, 1);
        });
        Assertions.assertThrows(Exception.class, () -> {
            CfxAddress.encode("invalid len".getBytes(), 1);
        });

        // decode: null, without :, invalid base32, invalid length, invalid checksum
        Assertions.assertThrows(Exception.class, () -> {
            CfxAddress.decode(null);
        });
        Assertions.assertThrows(Exception.class, () -> {
            CfxAddress.decode("012345");
        });
        Assertions.assertThrows(Exception.class, () -> {
            CfxAddress.decode("cfx:0206ujfsa1a11uuecwen3xytdmp8f03v14ksvfy");
        });
        Assertions.assertThrows(Exception.class, () -> {
            CfxAddress.decode("cfx:0206ujfsa1a11uuecwen3xytdmp8f03v14ksvfyl");
        });
        Assertions.assertThrows(Exception.class, () -> {
            CfxAddress.decode("cfx:0206ujfsa1a");
        });
        Assertions.assertThrows(Exception.class, () -> {
            CfxAddress.decode("cfx:0048g000000000000000000000000000087t3jt2fd");
        });
    }
}

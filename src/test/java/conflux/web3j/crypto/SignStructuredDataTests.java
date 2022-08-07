package conflux.web3j.crypto;

import conflux.web3j.Cfx;
import org.junit.jupiter.api.Test;
import org.web3j.crypto.*;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import static conflux.web3j.crypto.ECRecoverTest.keyPair;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.web3j.crypto.Sign.signMessage;

public class SignStructuredDataTests {
    private String getResource(String jsonFile) throws IOException {
        return new String(
                Files.readAllBytes(Paths.get(jsonFile).toAbsolutePath()), StandardCharsets.UTF_8);
    }
    // EIP712 v3
    @Test
    public void testValidStructure() throws IOException {
        StructuredDataEncoder dataEncoder =
                new StructuredDataEncoder(
                        getResource(
                                "build/resources/test/"
                                        + "structured_data_json_files/ValidStructuredData.json"));

        assertEquals(
                "0xf930c72ca47e411d8671f3bee80e1d7594cd17a04355b15db5f11c2aba0a54e9",
                Numeric.toHexString(dataEncoder.hashStructuredData()));
    }

    // EIP712 v3
    @Test
    public void testInValidStructure() throws IOException {
        StructuredDataEncoder dataEncoder =
                new StructuredDataEncoder(
                        getResource(
                                "build/resources/test/"
                                        + "structured_data_json_files/InValidStructuredData.json"));

        assertEquals(
                "0xf930c72ca47e411d8671f3bee80e1d7594cd17a04355b15db5f11c2aba0a54e9",
                Numeric.toHexString(dataEncoder.hashStructuredData()));
    }

    //eth_signTypedData_v4
    @Test
    public void testSignValidStructure() throws IOException {
        String msg = getResource(
                "build/resources/test/"
                        + "structured_data_json_files/ValidStructuredData.json");
        StructuredDataEncoder dataEncoder = new StructuredDataEncoder(msg);
        org.web3j.crypto.Sign.SignatureData sign = org.web3j.crypto.Sign.signMessage(dataEncoder.hashStructuredData(), keyPair, false);
        System.out.format("Signature: %s, %s, %s \n", Numeric.toHexString(sign.getR()), Numeric.toHexString(sign.getS()), Numeric.toHexString(sign.getV()));
    }

    //personal_sign
    @Test
    public void testSignAnyMessage() throws IOException {
        String message = "v0G9u7huK4mJb2K1";
        System.out.println(message);
        org.web3j.crypto.Sign.SignatureData sign = conflux.web3j.crypto.Sign.signPrefixedMessage(message.getBytes(), keyPair);
        System.out.format("Signature: %s, %s, %s \n", Numeric.toHexString(sign.getR()), Numeric.toHexString(sign.getS()), Numeric.toHexString(sign.getV()));
    }

}

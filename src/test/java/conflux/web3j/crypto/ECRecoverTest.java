package conflux.web3j.crypto;

import org.junit.jupiter.api.Test;
import org.web3j.crypto.*;
import org.web3j.crypto.Sign;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ECRecoverTest {
    public static final String PRIVATE_KEY_STRING =
            "cb8fdf376eed1c2b13ef305e817a61c9fb22b6d65685214c7a0c0d90a074db51"; //replace with your own private key to test through fluent
    static final BigInteger PRIVATE_KEY = Numeric.toBigInt(PRIVATE_KEY_STRING);

    static final ECKeyPair keyPair = ECKeyPair.create(PRIVATE_KEY);
    private String getAddress() {
        return Numeric.prependHexPrefix(Keys.getAddress(getPubKey()));
    }

    private String getPubKey() {
        return keyPair.getPublicKey().toString();
    }


    @Test
    public void testSignAndRecoverMessage() {
        String message = "v0G9u7huK4mJb2K1";

        byte[] msgHash = conflux.web3j.crypto.Sign.getConfluxMessageHash(message.getBytes());

        Sign.SignatureData sign = conflux.web3j.crypto.Sign.signPrefixedMessage(message.getBytes(), keyPair);

        boolean match = conflux.web3j.crypto.Sign.recoverSignature(sign, msgHash, getAddress());
        assertTrue(match);
    }

    @Test
    public void testRecoverTyped() throws IOException {
        StructuredDataTests t = new StructuredDataTests();
        String msg = t.getResource(
                "build/resources/test/"
                        + "structured_data_json_files/ValidStructuredData.json");
        StructuredDataEncoder dataEncoder = new StructuredDataEncoder(msg);

        //signature comes from `fluent`
        String signature =
                "0x7f28d98e75cdcaee68354d6ad0b9a2e8c4a3d365fb10fb70a1bc03a72bdb70de5b6d6587c7af57994c494ca3a1672e17d3f8f013e20641a7299f0d427a39a39001";

        byte[] signatureBytes = Numeric.hexStringToByteArray(signature);
        byte v = signatureBytes[64];
        if (v < 27) {
            v += 27;
        }

        Sign.SignatureData sd =
                new Sign.SignatureData(
                        v,
                        (byte[]) Arrays.copyOfRange(signatureBytes, 0, 32),
                        (byte[]) Arrays.copyOfRange(signatureBytes, 32, 64));

        boolean match = conflux.web3j.crypto.Sign.recoverSignature(sd, dataEncoder.hashStructuredData(), getAddress());
        assertTrue(match);
    }

    @Test
    public void testSignAndRecoverTyped() throws IOException {
        StructuredDataTests t = new StructuredDataTests();
        String msg = t.getResource(
                "build/resources/test/"
                        + "structured_data_json_files/ValidStructuredData.json");
        StructuredDataEncoder dataEncoder = new StructuredDataEncoder(msg);
        Sign.SignatureData sign = Sign.signMessage(dataEncoder.hashStructuredData(), keyPair, false);

        boolean match = conflux.web3j.crypto.Sign.recoverSignature(sign, dataEncoder.hashStructuredData(), getAddress());
        assertTrue(match);
    }
}

package conflux.web3j.crypto;

import org.junit.jupiter.api.Test;
import org.web3j.utils.Numeric;

import java.io.IOException;

import static conflux.web3j.crypto.ECRecoverTest.keyPair;

public class SignDataTests {

    //cfx_signTypedData_v4
    @Test
    public void testSignValidStructure() throws IOException {
        StructuredDataTests t = new StructuredDataTests();

        String msg = t.getResource(
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
        org.web3j.crypto.Sign.SignatureData sign = Sign.signPrefixedMessage(message.getBytes(), keyPair);
        System.out.format("Signature: %s, %s, %s \n", Numeric.toHexString(sign.getR()), Numeric.toHexString(sign.getS()), Numeric.toHexString(sign.getV()));
    }
}

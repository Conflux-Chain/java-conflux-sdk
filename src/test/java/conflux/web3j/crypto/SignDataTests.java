package conflux.web3j.crypto;

import org.junit.jupiter.api.Test;
import org.web3j.utils.Numeric;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SignDataTests {

    //cfx_signTypedData_v4
    @Test
    public void testSignValidStructure() throws IOException {
        StructuredDataTests t = new StructuredDataTests();

        String msg = t.getResource(
                "build/resources/test/"
                        + "structured_data_json_files/ValidStructuredData.json");
        StructuredDataEncoder dataEncoder = new StructuredDataEncoder(msg);
        org.web3j.crypto.Sign.SignatureData sign = org.web3j.crypto.Sign.signMessage(dataEncoder.hashStructuredData(), SampleKeys.KEY_PAIR, false);
        assertEquals(
                "0x371ef48d63082d3875fee13b392c5b6a7449aa638921cb9f3d419f5b6a817ba754d085965fb3a041c3b178d3ae3798ea322ae74cb687dd699b5f6045c7fe47a91c",
                Numeric.toHexString(sign.getR()) + Numeric.toHexStringNoPrefix(sign.getS()) + Numeric.toHexStringNoPrefix(sign.getV()));
    }

    //personal_sign
    @Test
    public void testSignAnyMessage() throws IOException {
        String message = "v0G9u7huK4mJb2K1";
        org.web3j.crypto.Sign.SignatureData sign = Sign.signPrefixedMessage(message.getBytes(), SampleKeys.KEY_PAIR);
        assertEquals(
                "0xbb0ee8492623f2ef6ed461ea638f8b5060b191a1c8830c93d84245f3fb27e20a755e24ff60fe76482dd4377a0aef036937ef88537b2d0fdd834a54e76ecafadc1c",
                Numeric.toHexString(sign.getR()) + Numeric.toHexStringNoPrefix(sign.getS()) + Numeric.toHexStringNoPrefix(sign.getV()));
    }
}

package conflux.web3j.types;

import com.google.common.io.BaseEncoding;
import conflux.web3j.crypto.ConfluxBase32;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CfxBase32Tests {
    @Test
    @DisplayName("CfxBase32Encode")
    void cfxBase32() throws Exception {
        byte[] testBytes = "123456".getBytes();
        assertEquals("ge3dgrbzg2", ConfluxBase32.encode(testBytes), "");
        assertEquals(true, Arrays.equals(ConfluxBase32.decode("ge3dgrbzg2"), testBytes), "");
    }
}

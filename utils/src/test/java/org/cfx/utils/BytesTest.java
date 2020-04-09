package org.cfx.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.cfx.utils.Bytes.trimLeadingZeroes;

public class BytesTest {

    @Test
    public void testTrimLeadingZeroes() {
        assertEquals(trimLeadingZeroes(new byte[] {}), (new byte[] {}));
        assertEquals(trimLeadingZeroes(new byte[] {0}), (new byte[] {0}));
        assertEquals(trimLeadingZeroes(new byte[] {1}), (new byte[] {1}));
        assertEquals(trimLeadingZeroes(new byte[] {0, 1}), (new byte[] {1}));
        assertEquals(trimLeadingZeroes(new byte[] {0, 0, 1}), (new byte[] {1}));
        assertEquals(trimLeadingZeroes(new byte[] {0, 0, 1, 0}), (new byte[] {1, 0}));
    }
}

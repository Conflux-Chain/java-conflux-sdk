package org.cfx.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.cfx.utils.Assertions.verifyPrecondition;

public class AssertionsTest {

    @Test
    public void testVerifyPrecondition() {
        verifyPrecondition(true, "");
    }

    @Test
    public void testVerifyPreconditionFailure() {

        assertThrows(RuntimeException.class, () -> verifyPrecondition(false, ""));
    }
}

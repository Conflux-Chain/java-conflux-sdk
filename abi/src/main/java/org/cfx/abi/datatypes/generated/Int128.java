package org.cfx.abi.datatypes.generated;

import java.math.BigInteger;
import org.cfx.abi.datatypes.Int;

public class Int128 extends Int {
    public static final Int128 DEFAULT = new Int128(BigInteger.ZERO);

    public Int128(BigInteger value) {
        super(128, value);
    }

    public Int128(long value) {
        this(BigInteger.valueOf(value));
    }
}

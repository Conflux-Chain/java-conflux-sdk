package org.cfx.abi.datatypes.generated;

import java.math.BigInteger;
import org.cfx.abi.datatypes.Int;

public class Int24 extends Int {
    public static final Int24 DEFAULT = new Int24(BigInteger.ZERO);

    public Int24(BigInteger value) {
        super(24, value);
    }

    public Int24(long value) {
        this(BigInteger.valueOf(value));
    }
}

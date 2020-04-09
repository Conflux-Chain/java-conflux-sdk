package org.cfx.abi.datatypes.generated;

import java.math.BigInteger;
import org.cfx.abi.datatypes.Int;

public class Int120 extends Int {
    public static final Int120 DEFAULT = new Int120(BigInteger.ZERO);

    public Int120(BigInteger value) {
        super(120, value);
    }

    public Int120(long value) {
        this(BigInteger.valueOf(value));
    }
}

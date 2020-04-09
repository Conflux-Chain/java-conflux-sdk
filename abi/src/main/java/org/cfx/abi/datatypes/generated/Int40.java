package org.cfx.abi.datatypes.generated;

import java.math.BigInteger;
import org.cfx.abi.datatypes.Int;

public class Int40 extends Int {
    public static final Int40 DEFAULT = new Int40(BigInteger.ZERO);

    public Int40(BigInteger value) {
        super(40, value);
    }

    public Int40(long value) {
        this(BigInteger.valueOf(value));
    }
}

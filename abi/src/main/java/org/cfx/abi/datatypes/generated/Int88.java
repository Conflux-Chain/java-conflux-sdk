package org.cfx.abi.datatypes.generated;

import java.math.BigInteger;
import org.cfx.abi.datatypes.Int;


public class Int88 extends Int {
    public static final Int88 DEFAULT = new Int88(BigInteger.ZERO);

    public Int88(BigInteger value) {
        super(88, value);
    }

    public Int88(long value) {
        this(BigInteger.valueOf(value));
    }
}

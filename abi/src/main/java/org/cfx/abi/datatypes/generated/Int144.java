package org.cfx.abi.datatypes.generated;

import java.math.BigInteger;
import org.cfx.abi.datatypes.Int;

public class Int144 extends Int {
    public static final Int144 DEFAULT = new Int144(BigInteger.ZERO);

    public Int144(BigInteger value) {
        super(144, value);
    }

    public Int144(long value) {
        this(BigInteger.valueOf(value));
    }
}

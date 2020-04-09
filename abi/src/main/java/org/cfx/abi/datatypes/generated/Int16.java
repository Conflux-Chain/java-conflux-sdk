package org.cfx.abi.datatypes.generated;

import java.math.BigInteger;

import org.cfx.abi.datatypes.Int;

public class Int16 extends Int {
    public static final Int16 DEFAULT = new Int16(BigInteger.ZERO);

    public Int16(BigInteger value) {
        super(16, value);
    }

    public Int16(long value) {
        this(BigInteger.valueOf(value));
    }
}

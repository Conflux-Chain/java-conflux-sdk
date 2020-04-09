package org.cfx.abi.datatypes.generated;

import java.math.BigInteger;

import org.cfx.abi.datatypes.Int;


public class Int32 extends Int {
    public static final Int32 DEFAULT = new Int32(BigInteger.ZERO);

    public Int32(BigInteger value) {
        super(32, value);
    }

    public Int32(long value) {
        this(BigInteger.valueOf(value));
    }
}

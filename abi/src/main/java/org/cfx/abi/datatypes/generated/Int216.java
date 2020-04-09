package org.cfx.abi.datatypes.generated;

import java.math.BigInteger;
import org.cfx.abi.datatypes.Int;


public class Int216 extends Int {
    public static final Int216 DEFAULT = new Int216(BigInteger.ZERO);

    public Int216(BigInteger value) {
        super(216, value);
    }

    public Int216(long value) {
        this(BigInteger.valueOf(value));
    }
}

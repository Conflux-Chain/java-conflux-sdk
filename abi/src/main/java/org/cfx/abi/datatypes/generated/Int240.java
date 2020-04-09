package org.cfx.abi.datatypes.generated;

import java.math.BigInteger;
import org.cfx.abi.datatypes.Int;

public class Int240 extends Int {
    public static final Int240 DEFAULT = new Int240(BigInteger.ZERO);

    public Int240(BigInteger value) {
        super(240, value);
    }

    public Int240(long value) {
        this(BigInteger.valueOf(value));
    }
}

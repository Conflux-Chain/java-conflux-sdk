package org.cfx.abi.datatypes.generated;

import java.math.BigInteger;
import org.cfx.abi.datatypes.Int;

public class Int232 extends Int {
    public static final Int232 DEFAULT = new Int232(BigInteger.ZERO);

    public Int232(BigInteger value) {
        super(232, value);
    }

    public Int232(long value) {
        this(BigInteger.valueOf(value));
    }
}

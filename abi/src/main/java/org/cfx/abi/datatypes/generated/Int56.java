package org.cfx.abi.datatypes.generated;

import java.math.BigInteger;
import org.cfx.abi.datatypes.Int;

public class Int56 extends Int {
    public static final Int56 DEFAULT = new Int56(BigInteger.ZERO);

    public Int56(BigInteger value) {
        super(56, value);
    }

    public Int56(long value) {
        this(BigInteger.valueOf(value));
    }
}

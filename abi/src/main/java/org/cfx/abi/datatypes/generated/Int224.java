package org.cfx.abi.datatypes.generated;

import java.math.BigInteger;
import org.cfx.abi.datatypes.Int;

public class Int224 extends Int {
    public static final Int224 DEFAULT = new Int224(BigInteger.ZERO);

    public Int224(BigInteger value) {
        super(224, value);
    }

    public Int224(long value) {
        this(BigInteger.valueOf(value));
    }
}

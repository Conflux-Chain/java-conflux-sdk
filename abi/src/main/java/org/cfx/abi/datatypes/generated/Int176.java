package org.cfx.abi.datatypes.generated;

import java.math.BigInteger;
import org.cfx.abi.datatypes.Int;

public class Int176 extends Int {
    public static final Int176 DEFAULT = new Int176(BigInteger.ZERO);

    public Int176(BigInteger value) {
        super(176, value);
    }

    public Int176(long value) {
        this(BigInteger.valueOf(value));
    }
}

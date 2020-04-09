package org.cfx.abi.datatypes.generated;

import java.math.BigInteger;
import org.cfx.abi.datatypes.Int;


public class Int64 extends Int {
    public static final Int64 DEFAULT = new Int64(BigInteger.ZERO);

    public Int64(BigInteger value) {
        super(64, value);
    }

    public Int64(long value) {
        this(BigInteger.valueOf(value));
    }
}

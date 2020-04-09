package org.cfx.abi.datatypes.generated;

import java.math.BigInteger;
import org.cfx.abi.datatypes.Int;


public class Int80 extends Int {
    public static final Int80 DEFAULT = new Int80(BigInteger.ZERO);

    public Int80(BigInteger value) {
        super(80, value);
    }

    public Int80(long value) {
        this(BigInteger.valueOf(value));
    }
}

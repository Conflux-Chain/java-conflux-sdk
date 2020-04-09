package org.cfx.abi.datatypes.generated;

import java.math.BigInteger;
import org.cfx.abi.datatypes.Int;

public class Int152 extends Int {
    public static final Int152 DEFAULT = new Int152(BigInteger.ZERO);

    public Int152(BigInteger value) {
        super(152, value);
    }

    public Int152(long value) {
        this(BigInteger.valueOf(value));
    }
}

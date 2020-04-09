package org.cfx.abi.datatypes.generated;

import java.math.BigInteger;
import org.cfx.abi.datatypes.Uint;

public class Uint232 extends Uint {
    public static final Uint232 DEFAULT = new Uint232(BigInteger.ZERO);

    public Uint232(BigInteger value) {
        super(232, value);
    }

    public Uint232(long value) {
        this(BigInteger.valueOf(value));
    }
}

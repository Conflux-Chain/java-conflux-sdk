package org.cfx.abi.datatypes.generated;

import java.math.BigInteger;
import org.cfx.abi.datatypes.Uint;

public class Uint112 extends Uint {
    public static final Uint112 DEFAULT = new Uint112(BigInteger.ZERO);

    public Uint112(BigInteger value) {
        super(112, value);
    }

    public Uint112(long value) {
        this(BigInteger.valueOf(value));
    }
}

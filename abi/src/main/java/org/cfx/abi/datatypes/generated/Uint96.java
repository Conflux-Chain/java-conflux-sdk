package org.cfx.abi.datatypes.generated;

import java.math.BigInteger;
import org.cfx.abi.datatypes.Uint;

public class Uint96 extends Uint {
    public static final Uint96 DEFAULT = new Uint96(BigInteger.ZERO);

    public Uint96(BigInteger value) {
        super(96, value);
    }

    public Uint96(long value) {
        this(BigInteger.valueOf(value));
    }
}

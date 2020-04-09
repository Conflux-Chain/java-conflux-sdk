package org.cfx.abi.datatypes.generated;

import java.math.BigInteger;
import org.cfx.abi.datatypes.Uint;

public class Uint224 extends Uint {
    public static final Uint224 DEFAULT = new Uint224(BigInteger.ZERO);

    public Uint224(BigInteger value) {
        super(224, value);
    }

    public Uint224(long value) {
        this(BigInteger.valueOf(value));
    }
}

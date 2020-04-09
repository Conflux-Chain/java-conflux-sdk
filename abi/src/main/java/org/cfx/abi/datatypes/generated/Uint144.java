package org.cfx.abi.datatypes.generated;

import java.math.BigInteger;
import org.cfx.abi.datatypes.Uint;

public class Uint144 extends Uint {
    public static final Uint144 DEFAULT = new Uint144(BigInteger.ZERO);

    public Uint144(BigInteger value) {
        super(144, value);
    }

    public Uint144(long value) {
        this(BigInteger.valueOf(value));
    }
}

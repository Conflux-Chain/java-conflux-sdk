package org.cfx.abi.datatypes.generated;

import java.math.BigInteger;
import org.cfx.abi.datatypes.Uint;

public class Uint8 extends Uint {
    public static final Uint8 DEFAULT = new Uint8(BigInteger.ZERO);

    public Uint8(BigInteger value) {
        super(8, value);
    }

    public Uint8(long value) {
        this(BigInteger.valueOf(value));
    }
}

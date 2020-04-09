package org.cfx.abi.datatypes.generated;

import java.math.BigInteger;
import org.cfx.abi.datatypes.Uint;

public class Uint192 extends Uint {
    public static final Uint192 DEFAULT = new Uint192(BigInteger.ZERO);

    public Uint192(BigInteger value) {
        super(192, value);
    }

    public Uint192(long value) {
        this(BigInteger.valueOf(value));
    }
}

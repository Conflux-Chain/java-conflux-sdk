package org.cfx.abi.datatypes.generated;

import java.math.BigInteger;
import org.cfx.abi.datatypes.Uint;

public class Uint56 extends Uint {
    public static final Uint56 DEFAULT = new Uint56(BigInteger.ZERO);

    public Uint56(BigInteger value) {
        super(56, value);
    }

    public Uint56(long value) {
        this(BigInteger.valueOf(value));
    }
}

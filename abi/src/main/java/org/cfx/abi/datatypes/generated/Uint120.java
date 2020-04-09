package org.cfx.abi.datatypes.generated;

import java.math.BigInteger;

import org.cfx.abi.datatypes.Uint;

public class Uint120 extends Uint {
    public static final Uint120 DEFAULT = new Uint120(BigInteger.ZERO);

    public Uint120(BigInteger value) {
        super(120, value);
    }

    public Uint120(long value) {
        this(BigInteger.valueOf(value));
    }
}

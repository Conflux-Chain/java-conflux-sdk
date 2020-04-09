package org.cfx.abi.datatypes.generated;

import java.math.BigInteger;

import org.cfx.abi.datatypes.Uint;

public class Uint240 extends Uint {
    public static final Uint240 DEFAULT = new Uint240(BigInteger.ZERO);

    public Uint240(BigInteger value) {
        super(240, value);
    }

    public Uint240(long value) {
        this(BigInteger.valueOf(value));
    }
}

package org.cfx.abi.datatypes.generated;

import java.math.BigInteger;

import org.cfx.abi.datatypes.Uint;

public class Uint152 extends Uint {
    public static final Uint152 DEFAULT = new Uint152(BigInteger.ZERO);

    public Uint152(BigInteger value) {
        super(152, value);
    }

    public Uint152(long value) {
        this(BigInteger.valueOf(value));
    }
}

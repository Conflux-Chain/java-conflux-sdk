package org.cfx.abi.datatypes.generated;

import java.math.BigInteger;

import org.cfx.abi.datatypes.Uint;

public class Uint80 extends Uint {
    public static final Uint80 DEFAULT = new Uint80(BigInteger.ZERO);

    public Uint80(BigInteger value) {
        super(80, value);
    }

    public Uint80(long value) {
        this(BigInteger.valueOf(value));
    }
}

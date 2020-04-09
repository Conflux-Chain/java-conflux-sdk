package org.cfx.abi.datatypes.generated;

import java.math.BigInteger;
import org.cfx.abi.datatypes.Uint;

public class Uint176 extends Uint {
    public static final Uint176 DEFAULT = new Uint176(BigInteger.ZERO);

    public Uint176(BigInteger value) {
        super(176, value);
    }

    public Uint176(long value) {
        this(BigInteger.valueOf(value));
    }
}

package org.cfx.abi.datatypes.generated;

import java.math.BigInteger;
import org.cfx.abi.datatypes.Uint;

public class Uint72 extends Uint {
    public static final Uint72 DEFAULT = new Uint72(BigInteger.ZERO);

    public Uint72(BigInteger value) {
        super(72, value);
    }

    public Uint72(long value) {
        this(BigInteger.valueOf(value));
    }
}

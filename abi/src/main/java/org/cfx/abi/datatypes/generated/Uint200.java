package org.cfx.abi.datatypes.generated;

import java.math.BigInteger;
import org.cfx.abi.datatypes.Uint;

public class Uint200 extends Uint {
    public static final Uint200 DEFAULT = new Uint200(BigInteger.ZERO);

    public Uint200(BigInteger value) {
        super(200, value);
    }

    public Uint200(long value) {
        this(BigInteger.valueOf(value));
    }
}

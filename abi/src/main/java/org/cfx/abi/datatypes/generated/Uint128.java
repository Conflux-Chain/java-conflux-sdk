package org.cfx.abi.datatypes.generated;

import java.math.BigInteger;
import org.cfx.abi.datatypes.Uint;

public class Uint128 extends Uint {
    public static final Uint128 DEFAULT = new Uint128(BigInteger.ZERO);

    public Uint128(BigInteger value) {
        super(128, value);
    }

    public Uint128(long value) {
        this(BigInteger.valueOf(value));
    }
}

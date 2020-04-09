package org.cfx.abi.datatypes.primitive;

import org.cfx.abi.datatypes.NumericType;
import org.cfx.abi.datatypes.generated.Int64;

public final class Long extends Number<java.lang.Long> {

    public Long(long value) {
        super(value);
    }

    @Override
    public NumericType toSolidityType() {
        return new Int64(getValue());
    }
}

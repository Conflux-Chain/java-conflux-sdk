
package org.cfx.abi.datatypes.primitive;

import org.cfx.abi.datatypes.NumericType;

public class Float extends Number<java.lang.Float> {

    public Float(float value) {
        super(value);
    }

    @Override
    public NumericType toSolidityType() {
        throw new UnsupportedOperationException("Fixed types are not supported");
    }
}


package org.cfx.abi.datatypes.primitive;

import org.cfx.abi.datatypes.NumericType;

public final class Double extends Number<java.lang.Double> {

    public Double(double value) {
        super(value);
    }

    @Override
    public NumericType toSolidityType() {
        throw new UnsupportedOperationException("Fixed types are not supported");
    }
}

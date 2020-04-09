
package org.cfx.abi.datatypes.primitive;

import org.cfx.abi.datatypes.NumericType;
import org.cfx.abi.datatypes.generated.Int16;

public final class Short extends Number<java.lang.Short> {

    public Short(short value) {
        super(value);
    }

    @Override
    public NumericType toSolidityType() {
        return new Int16(getValue());
    }
}

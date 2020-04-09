
package org.cfx.abi.datatypes.primitive;

import org.cfx.abi.datatypes.Type;
import org.cfx.abi.datatypes.generated.Bytes1;

public final class Byte extends PrimitiveType<java.lang.Byte> {

    public Byte(byte value) {
        super(value);
    }

    @Override
    public Type toSolidityType() {
        return new Bytes1(new byte[] {getValue()});
    }
}

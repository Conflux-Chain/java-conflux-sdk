
package org.cfx.abi.datatypes.primitive;

import org.cfx.abi.datatypes.Type;
import org.cfx.abi.datatypes.Utf8String;

public final class Char extends PrimitiveType<Character> {

    public Char(char value) {
        super(value);
    }

    @Override
    public Type toSolidityType() {
        return new Utf8String(String.valueOf(getValue()));
    }
}

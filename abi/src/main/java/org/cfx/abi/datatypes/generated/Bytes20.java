package org.cfx.abi.datatypes.generated;

import org.cfx.abi.datatypes.Bytes;

public class Bytes20 extends Bytes {
    public static final Bytes20 DEFAULT = new Bytes20(new byte[20]);

    public Bytes20(byte[] value) {
        super(20, value);
    }
}

package org.cfx.abi.datatypes.generated;

import org.cfx.abi.datatypes.Bytes;

public class Bytes4 extends Bytes {
    public static final Bytes4 DEFAULT = new Bytes4(new byte[4]);

    public Bytes4(byte[] value) {
        super(4, value);
    }
}

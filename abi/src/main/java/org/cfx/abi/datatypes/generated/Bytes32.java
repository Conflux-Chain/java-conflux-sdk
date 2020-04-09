package org.cfx.abi.datatypes.generated;

import org.cfx.abi.datatypes.Bytes;

public class Bytes32 extends Bytes {
    public static final Bytes32 DEFAULT = new Bytes32(new byte[32]);

    public Bytes32(byte[] value) {
        super(32, value);
    }
}

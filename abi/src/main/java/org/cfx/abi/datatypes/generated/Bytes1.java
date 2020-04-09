package org.cfx.abi.datatypes.generated;

import org.cfx.abi.datatypes.Bytes;


public class Bytes1 extends Bytes {
    public static final Bytes1 DEFAULT = new Bytes1(new byte[1]);

    public Bytes1(byte[] value) {
        super(1, value);
    }
}

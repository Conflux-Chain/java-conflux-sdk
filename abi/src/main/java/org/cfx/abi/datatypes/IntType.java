package org.cfx.abi.datatypes;

import java.math.BigInteger;

/** Common integer properties. */
public abstract class IntType extends NumericType {

    private final int bitSize;

    public IntType(String typePrefix, int bitSize, BigInteger value) {
        super(typePrefix + bitSize, value);
        this.bitSize = bitSize;
        if (!valid()) {
            throw new UnsupportedOperationException(
                    "Bit size must be 8 bit aligned, "
                            + "and in range 0 < bitSize <= "
                            + MAX_BIT_LENGTH);
        }
    }

    public int getBitSize() {
        return bitSize;
    }

    protected boolean valid() {
        return isValidBitSize(bitSize) && isValidBitCount(bitSize, value);
    }

    private static boolean isValidBitSize(int bitSize) {
        return bitSize % 8 == 0 && bitSize > 0 && bitSize <= MAX_BIT_LENGTH;
    }

    private static boolean isValidBitCount(int bitSize, BigInteger value) {
        return value.bitLength() <= bitSize;
    }
}

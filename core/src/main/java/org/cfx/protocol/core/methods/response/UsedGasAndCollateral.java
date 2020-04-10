package org.cfx.protocol.core.methods.response;
import org.cfx.utils.Numeric;

import java.math.BigInteger;

public class UsedGasAndCollateral {
    private String gasUsed;
    private String storageCollateralized;

    public BigInteger getGasUsed() {
        return Numeric.decodeQuantity(this.gasUsed);
    }

    public void setGasUsed(String gasUsed) {
        this.gasUsed = gasUsed;
    }

    public BigInteger getStorageCollateralized() {
        return new BigInteger(this.storageCollateralized);
    }

    public void setStorageCollateralized(String storageCollateralized) {
        this.storageCollateralized = storageCollateralized;
    }

    @Override
    public String toString() {
        return String.format("{gasUsed = %s, storageCollateralized = %s}", this.gasUsed, this.storageCollateralized);
    }
}
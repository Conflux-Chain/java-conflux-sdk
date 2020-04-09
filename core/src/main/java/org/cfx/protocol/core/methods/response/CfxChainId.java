package org.cfx.protocol.core.methods.response;

import org.cfx.protocol.core.Response;
import org.cfx.utils.Numeric;

import java.math.BigInteger;

public class CfxChainId extends Response<String> {
    public BigInteger getChainId() {
        return Numeric.decodeQuantity(getResult());
    }
}

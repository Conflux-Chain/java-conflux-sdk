package org.cfx.protocol.core.methods.response;

import java.math.BigInteger;

import org.cfx.protocol.core.Response;
import org.cfx.utils.Numeric;

/** shh_newFilter. */
public class ShhNewFilter extends Response<String> {

    public BigInteger getFilterId() {
        return Numeric.decodeQuantity(getResult());
    }
}

package org.cfx.protocol.core.methods.response;

import java.math.BigInteger;

import org.cfx.protocol.core.Response;
import org.cfx.utils.Numeric;

/** net_peerCount. */
public class NetPeerCount extends Response<String> {

    public BigInteger getQuantity() {
        return Numeric.decodeQuantity(getResult());
    }
}

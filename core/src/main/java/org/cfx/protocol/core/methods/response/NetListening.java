package org.cfx.protocol.core.methods.response;

import org.cfx.protocol.core.Response;

/** net_listening. */
public class NetListening extends Response<Boolean> {
    public boolean isListening() {
        return getResult();
    }
}

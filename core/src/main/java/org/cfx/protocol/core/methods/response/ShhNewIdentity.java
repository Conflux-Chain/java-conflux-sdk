package org.cfx.protocol.core.methods.response;

import org.cfx.protocol.core.Response;

/** shh_newIdentity. */
public class ShhNewIdentity extends Response<String> {

    public String getAddress() {
        return getResult();
    }
}

package org.cfx.protocol.core.methods.response;

import org.cfx.protocol.core.Response;

/** shh_hasIdentity. */
public class ShhHasIdentity extends Response<Boolean> {

    public boolean hasPrivateKeyForIdentity() {
        return getResult();
    }
}

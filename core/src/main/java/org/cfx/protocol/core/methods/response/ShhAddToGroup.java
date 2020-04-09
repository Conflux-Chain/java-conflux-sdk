package org.cfx.protocol.core.methods.response;

import org.cfx.protocol.core.Response;

/** shh_addToGroup. */
public class ShhAddToGroup extends Response<Boolean> {

    public boolean addedToGroup() {
        return getResult();
    }
}

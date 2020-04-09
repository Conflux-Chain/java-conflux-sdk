package org.cfx.protocol.core.methods.response;

import org.cfx.protocol.core.Response;

/** shh_newGroup. */
public class ShhNewGroup extends Response<String> {

    public String getAddress() {
        return getResult();
    }
}

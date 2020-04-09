package org.cfx.protocol.core.methods.response;

import org.cfx.protocol.core.Response;

/** shh_post. */
public class ShhPost extends Response<Boolean> {

    public boolean messageSent() {
        return getResult();
    }
}

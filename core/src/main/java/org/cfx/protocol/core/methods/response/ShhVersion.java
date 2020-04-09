package org.cfx.protocol.core.methods.response;

import org.cfx.protocol.core.Response;

/** shh_version. */
public class ShhVersion extends Response<String> {

    public String getVersion() {
        return getResult();
    }
}

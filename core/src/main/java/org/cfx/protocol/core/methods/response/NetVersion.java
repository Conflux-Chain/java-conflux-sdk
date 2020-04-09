package org.cfx.protocol.core.methods.response;

import org.cfx.protocol.core.Response;

/** net_version. */
public class NetVersion extends Response<String> {
    public String getNetVersion() {
        return getResult();
    }
}

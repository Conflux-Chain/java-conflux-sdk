package org.cfx.protocol.core.methods.response;

import org.cfx.protocol.core.Response;

public class CfxProtocolVersion extends Response<String> {
    public String getProtocolVersion() {
        return getResult();
    }
}

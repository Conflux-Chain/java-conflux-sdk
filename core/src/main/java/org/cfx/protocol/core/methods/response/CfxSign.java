package org.cfx.protocol.core.methods.response;

import org.cfx.protocol.core.Response;

public class CfxSign extends Response<String> {
    public String getSignature() {
        return getResult();
    }
}

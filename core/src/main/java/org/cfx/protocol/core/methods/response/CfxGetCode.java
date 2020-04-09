package org.cfx.protocol.core.methods.response;

import org.cfx.protocol.core.Response;

public class CfxGetCode extends Response<String> {
    public String getCode() {
        return getResult();
    }
}

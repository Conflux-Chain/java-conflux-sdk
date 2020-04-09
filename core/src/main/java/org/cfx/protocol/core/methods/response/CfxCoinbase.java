package org.cfx.protocol.core.methods.response;

import org.cfx.protocol.core.Response;

public class CfxCoinbase extends Response<String> {
    public String getAddress() {
        return getResult();
    }
}

package org.cfx.protocol.core.methods.response;

import org.cfx.protocol.core.Response;

public class CfxGetStorageAt extends Response<String> {
    public String getData() {
        return getResult();
    }
}

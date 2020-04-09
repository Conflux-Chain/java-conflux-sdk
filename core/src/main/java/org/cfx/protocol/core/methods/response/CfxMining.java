package org.cfx.protocol.core.methods.response;

import org.cfx.protocol.core.Response;

public class CfxMining extends Response<Boolean> {
    public boolean isMining() {
        return getResult();
    }
}


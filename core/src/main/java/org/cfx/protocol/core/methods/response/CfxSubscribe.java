package org.cfx.protocol.core.methods.response;

import org.cfx.protocol.core.Response;

public class CfxSubscribe extends Response<String> {
    public String getSubscriptionId() {
        return getResult();
    }
}

package org.cfx.protocol.core.methods.response;

import org.cfx.protocol.core.Response;

public class CfxSendRawTransaction extends Response<String> {
    public String getTransactionHash() {
        return getResult();
    }
}

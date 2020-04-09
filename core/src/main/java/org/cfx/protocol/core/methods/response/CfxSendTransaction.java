package org.cfx.protocol.core.methods.response;

import org.cfx.protocol.core.Response;

public class CfxSendTransaction extends Response<String> {
    public String getTransactionHash() {
        return getResult();
    }
}

package org.cfx.protocol.core.methods.response;

import org.cfx.protocol.core.Response;

public class CfxClientVersion extends Response<String> {

    public String getWeb3ClientVersion() {
        return getResult();
    }
}

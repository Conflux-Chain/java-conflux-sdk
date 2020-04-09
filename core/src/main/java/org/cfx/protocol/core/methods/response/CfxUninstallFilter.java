package org.cfx.protocol.core.methods.response;

import org.cfx.protocol.core.Response;

public class CfxUninstallFilter extends Response<Boolean> {
    public boolean isUninstalled() {
        return getResult();
    }
}

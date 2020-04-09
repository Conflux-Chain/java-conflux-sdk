package org.cfx.protocol.core.methods.response;

import org.cfx.protocol.core.Response;

/** shh_uninstallFilter. */
public class ShhUninstallFilter extends Response<Boolean> {

    public boolean isUninstalled() {
        return getResult();
    }
}

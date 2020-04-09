package org.cfx.protocol.core.methods.response;

import org.cfx.protocol.core.Response;

/** db_getHex. */
public class DbGetHex extends Response<String> {

    public String getStoredValue() {
        return getResult();
    }
}

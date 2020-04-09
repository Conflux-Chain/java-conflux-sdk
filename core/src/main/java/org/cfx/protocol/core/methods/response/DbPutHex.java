package org.cfx.protocol.core.methods.response;

import org.cfx.protocol.core.Response;

/** db_putHex. */
public class DbPutHex extends Response<Boolean> {

    public boolean valueStored() {
        return getResult();
    }
}

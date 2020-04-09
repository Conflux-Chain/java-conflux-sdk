package org.cfx.protocol.core.methods.response;

import org.cfx.protocol.core.Response;

/** db_putString. */
public class DbPutString extends Response<Boolean> {

    public boolean valueStored() {
        return getResult();
    }
}

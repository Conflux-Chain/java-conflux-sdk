
package org.cfx.protocol.admin.methods.response;

import org.cfx.protocol.core.Response;

/** Boolean response type. */
public class BooleanResponse extends Response<Boolean> {
    public boolean success() {
        return getResult();
    }
}

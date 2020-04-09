package org.cfx.protocol.core.methods.response;

import org.cfx.protocol.core.Response;

/**
 * Null response object returned by the below methods.
 *
 * <ul>
 *   <li>personal_setAccountName
 *   <li>personal_setAccountMeta
 * </ul>
 */
public class VoidResponse extends Response<Void> {
    public boolean isValid() {
        return !hasError();
    }
}

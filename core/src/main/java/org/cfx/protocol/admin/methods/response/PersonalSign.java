package org.cfx.protocol.admin.methods.response;

import org.cfx.protocol.core.Response;

/** personal_sign parity_signMessage. */
public class PersonalSign extends Response<String> {
    public String getSignedMessage() {
        return getResult();
    }
}

package org.cfx.protocol.admin.methods.response;

import org.cfx.protocol.core.Response;

/** personal_unlockAccount. */
public class PersonalUnlockAccount extends Response<Boolean> {
    public Boolean accountUnlocked() {
        return getResult();
    }
}

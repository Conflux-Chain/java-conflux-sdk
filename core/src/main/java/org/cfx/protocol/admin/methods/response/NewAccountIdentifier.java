package org.cfx.protocol.admin.methods.response;

import org.cfx.protocol.core.Response;

/**
 * personal_newAccount parity_newAccountFromPhrase parity_newAccountFromSecret
 * parity_newAccountFromWallet.
 */
public class NewAccountIdentifier extends Response<String> {
    public String getAccountId() {
        return getResult();
    }
}

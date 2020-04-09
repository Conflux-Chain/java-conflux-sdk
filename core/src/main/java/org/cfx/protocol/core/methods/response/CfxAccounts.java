package org.cfx.protocol.core.methods.response;

import org.cfx.protocol.core.Response;

import java.util.List;

public class CfxAccounts extends Response<List<String>> {
    public List<String> getAccounts() {
        return getResult();
    }
}

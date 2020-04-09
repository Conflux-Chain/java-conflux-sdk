package org.cfx.protocol.core.methods.response;

import org.cfx.protocol.core.Response;

import java.util.List;

public class CfxGetWork extends Response<List<String>> {

    public String getCurrentBlockHeaderPowHash() {
        return getResult().get(0);
    }

    public String getSeedHashForDag() {
        return getResult().get(1);
    }

    public String getBoundaryCondition() {
        return getResult().get(2);
    }
}

package org.cfx.protocol.core.methods.response;

import org.cfx.protocol.core.Response;

public class CfxSubmitWork extends Response<Boolean> {

    public boolean solutionValid() {
        return getResult();
    }
}

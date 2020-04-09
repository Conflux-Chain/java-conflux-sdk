package org.cfx.protocol.core.methods.response;

import org.cfx.protocol.core.Response;

public class CfxCompileLLL extends Response<String> {
    public String getCompiledSourceCode() {
        return getResult();
    }
}


package org.cfx.protocol.core.methods.response;

import org.cfx.protocol.core.Response;

public class CfxCompileSerpent extends Response<String> {
    public String getCompiledSourceCode() {
        return getResult();
    }
}

package org.cfx.protocol.core.methods.response;

import org.cfx.protocol.core.Response;

import java.util.List;

public class CfxGetCompilers extends Response<List<String>> {
    public List<String> getCompilers() {
        return getResult();
    }
}


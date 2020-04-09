package org.cfx.protocol.core.filters;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.cfx.protocol.Cfx;
import org.cfx.protocol.core.Request;
import org.cfx.protocol.core.methods.response.CfxLog;
import org.cfx.protocol.core.methods.response.Log;
import  org.cfx.protocol.core.methods.response.CfxFilter;

/** Log filter handler. */
public class LogFilter extends Filter<Log> {

    private final org.cfx.protocol.core.methods.request.CfxFilter cfxFilter;

    public LogFilter(
            Cfx cfx,
            Callback<Log> callback,
            org.cfx.protocol.core.methods.request.CfxFilter cfxFilter) {
        super(cfx, callback);
        this.cfxFilter = cfxFilter;
    }

    @Override
    CfxFilter sendRequest() throws IOException {
        return cfx.cfxNewFilter(cfxFilter).send();
    }

    @Override
    void process(List<CfxLog.LogResult> logResults) {
        for (CfxLog.LogResult logResult : logResults) {
            if (logResult instanceof CfxLog.LogObject) {
                Log log = ((CfxLog.LogObject) logResult).get();
                callback.onEvent(log);
            } else {
                throw new FilterException(
                        "Unexpected result type: " + logResult.get() + " required LogObject");
            }
        }
    }

    @Override
    protected Optional<Request<?, CfxLog>> getFilterLogs(BigInteger filterId) {
        return Optional.of(cfx.cfxGetFilterLogs(filterId));
    }
}

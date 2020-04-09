package org.cfx.protocol.core.filters;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.cfx.protocol.Cfx;
import org.cfx.protocol.core.Request;
import org.cfx.protocol.core.methods.response.CfxFilter;
import org.cfx.protocol.core.methods.response.CfxLog;
import org.cfx.protocol.core.methods.response.Log;

/** Logs filter handler. */
public class LogsFilter extends Filter<List<Log>> {

    private final org.cfx.protocol.core.methods.request.CfxFilter cfxFilter;

    public LogsFilter(
            Cfx cfx,
            Callback<List<Log>> callback,
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
        List<Log> logs = new ArrayList<>(logResults.size());

        for (CfxLog.LogResult logResult : logResults) {
            if (!(logResult instanceof CfxLog.LogObject)) {
                throw new FilterException(
                        "Unexpected result type: " + logResult.get() + " required LogObject");
            }

            logs.add(((CfxLog.LogObject) logResult).get());
        }

        callback.onEvent(logs);
    }

    @Override
    protected Optional<Request<?, CfxLog>> getFilterLogs(BigInteger filterId) {
        return Optional.of(cfx.cfxGetFilterLogs(filterId));
    }
}

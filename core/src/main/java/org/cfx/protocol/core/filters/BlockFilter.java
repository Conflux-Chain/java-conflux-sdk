package org.cfx.protocol.core.filters;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.cfx.protocol.Cfx;
import org.cfx.protocol.core.Request;
import org.cfx.protocol.core.methods.response.CfxFilter;
import org.cfx.protocol.core.methods.response.CfxLog;

/** Handler for working with block filter requests. */
public class BlockFilter extends Filter<String> {

    public BlockFilter(Cfx cfx, Callback<String> callback) {
        super(cfx, callback);
    }

    @Override
    CfxFilter sendRequest() throws IOException {
        return cfx.cfxNewBlockFilter().send();
    }

    @Override
    void process(List<CfxLog.LogResult> logResults) {
        for (CfxLog.LogResult logResult : logResults) {
            if (logResult instanceof CfxLog.Hash) {
                String blockHash = ((CfxLog.Hash) logResult).get();
                callback.onEvent(blockHash);
            } else {
                throw new FilterException(
                        "Unexpected result type: " + logResult.get() + ", required Hash");
            }
        }
    }

    /**
     * Since the block filter does not support historic filters, the filterId is ignored and an
     * empty optional is returned.
     *
     * @param filterId Id of the filter for which the historic log should be retrieved
     * @return Optional.empty()
     */
    @Override
    protected Optional<Request<?, CfxLog>> getFilterLogs(BigInteger filterId) {
        return Optional.empty();
    }
}

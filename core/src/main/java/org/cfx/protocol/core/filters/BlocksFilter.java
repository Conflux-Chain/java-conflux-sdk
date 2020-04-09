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

/** Handler hashes for working with block filter requests */
public class BlocksFilter extends Filter<List<String>> {

    public BlocksFilter(Cfx cfx, Callback<List<String>> callback) {
        super(cfx, callback);
    }

    @Override
    CfxFilter sendRequest() throws IOException {
        return cfx.cfxNewBlockFilter().send();
    }

    @Override
    void process(List<CfxLog.LogResult> logResults) {
        List<String> blockHashes = new ArrayList<>(logResults.size());

        for (CfxLog.LogResult logResult : logResults) {
            if (!(logResult instanceof CfxLog.Hash)) {
                throw new FilterException(
                        "Unexpected result type: " + logResult.get() + ", required Hash");
            }

            blockHashes.add(((CfxLog.Hash) logResult).get());
        }

        callback.onEvent(blockHashes);
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

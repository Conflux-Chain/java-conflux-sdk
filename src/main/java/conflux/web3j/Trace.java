package conflux.web3j;

import conflux.web3j.request.TraceFilter;
import conflux.web3j.response.LocalizedBlockTrace;
import conflux.web3j.response.LocalizedTrace;

import java.io.Closeable;
import java.util.List;
import java.util.Optional;

public interface Trace  extends Closeable {
    Request<Optional<List<LocalizedTrace>>, LocalizedTrace.Response> traceTransaction(String txHash);

    Request<Optional<LocalizedBlockTrace>, LocalizedBlockTrace.Response> traceBlock(String blockHash);

    Request<Optional<List<LocalizedTrace>>, LocalizedTrace.Response> traceFilter(TraceFilter filter);
}

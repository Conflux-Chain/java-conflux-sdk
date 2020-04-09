package org.cfx.protocol.core;

import java.util.List;
import java.util.concurrent.Callable;

import org.cfx.abi.FunctionEncoder;
import org.cfx.abi.FunctionReturnDecoder;
import org.cfx.abi.datatypes.Function;
import org.cfx.abi.datatypes.Type;

/**
 * A wrapper for a callable function. Can also return the raw encoded function
 *
 * @param <T> Our return type.
 */
public class RemoteFunctionCall<T> extends RemoteCall<T> {

    private final Function function;

    public RemoteFunctionCall(Function function, Callable<T> callable) {
        super(callable);
        this.function = function;
    }

    /**
     * return an encoded function, so it can be manually signed and transmitted
     *
     * @return the function call, encoded.
     */
    public String encodeFunctionCall() {
        return FunctionEncoder.encode(function);
    }

    /**
     * decode a method response
     *
     * @param response the encoded response
     * @return list of abi types
     */
    public List<Type> decodeFunctionResponse(String response) {
        return FunctionReturnDecoder.decode(response, function.getOutputParameters());
    }
}

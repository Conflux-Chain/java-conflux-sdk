
package org.cfx.abi;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

import org.cfx.abi.datatypes.Type;
import org.cfx.abi.spi.FunctionReturnDecoderProvider;

/**
 * Decodes values returned by function or event calls.
 *
 * <p>Delegates to {@link DefaultFunctionReturnDecoder} unless a {@link
 * FunctionReturnDecoderProvider} SPI is found, in which case the first implementation found will be
 * used.
 *
 * @see DefaultFunctionReturnDecoder
 * @see FunctionReturnDecoderProvider
 */
public abstract class FunctionReturnDecoder {

    private static FunctionReturnDecoder DEFAULT_DECODER;

    private static final ServiceLoader<FunctionReturnDecoderProvider> loader =
            ServiceLoader.load(FunctionReturnDecoderProvider.class);

    /**
     * Decode ABI encoded return values from smart contract function call.
     *
     * @param rawInput ABI encoded input
     * @param outputParameters list of return types as {@link TypeReference}
     * @return {@link List} of values returned by function, {@link Collections#emptyList()} if
     *     invalid response
     */
    public static List<Type> decode(String rawInput, List<TypeReference<Type>> outputParameters) {
        return decoder().decodeFunctionResult(rawInput, outputParameters);
    }

    /**
     * Decodes an indexed parameter associated with an event. Indexed parameters are individually
     * encoded, unlike non-indexed parameters which are encoded as per ABI-encoded function
     * parameters and return values.
     *
     * <p>If any of the following types are indexed, the Keccak-256 hashes of the values are
     * returned instead. These are returned as a bytes32 value.
     *
     * <ul>
     *   <li>Arrays
     *   <li>Strings
     *   <li>Bytes
     * </ul>
     *
     * <p>See the <a href="http://solidity.readthedocs.io/en/latest/contracts.html#events">Solidity
     * documentation</a> for further information.
     *
     * @param rawInput ABI encoded input
     * @param typeReference of expected result type
     * @param <T> type of TypeReference
     * @return the decode value
     */
    public static <T extends Type> Type decodeIndexedValue(
            String rawInput, TypeReference<T> typeReference) {
        return decoder().decodeEventParameter(rawInput, typeReference);
    }

    protected abstract List<Type> decodeFunctionResult(
            String rawInput, List<TypeReference<Type>> outputParameters);

    protected abstract <T extends Type> Type decodeEventParameter(
            String rawInput, TypeReference<T> typeReference);

    private static FunctionReturnDecoder decoder() {
        final Iterator<FunctionReturnDecoderProvider> iterator = loader.iterator();
        return iterator.hasNext() ? iterator.next().get() : defaultDecoder();
    }

    private static FunctionReturnDecoder defaultDecoder() {
        if (DEFAULT_DECODER == null) {
            DEFAULT_DECODER = new DefaultFunctionReturnDecoder();
        }
        return DEFAULT_DECODER;
    }
}

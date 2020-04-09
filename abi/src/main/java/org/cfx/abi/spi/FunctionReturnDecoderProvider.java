
package org.cfx.abi.spi;

import java.util.function.Supplier;

import org.cfx.abi.FunctionReturnDecoder;

/** Function decoding Service Provider Interface. */
public interface FunctionReturnDecoderProvider extends Supplier<FunctionReturnDecoder> {}

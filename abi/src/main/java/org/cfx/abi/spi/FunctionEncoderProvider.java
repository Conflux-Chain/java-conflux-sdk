
package org.cfx.abi.spi;

import java.util.function.Supplier;

import org.cfx.abi.FunctionEncoder;

/** Function encoding Service Provider Interface. */
public interface FunctionEncoderProvider extends Supplier<FunctionEncoder> {}

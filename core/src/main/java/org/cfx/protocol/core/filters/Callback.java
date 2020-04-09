package org.cfx.protocol.core.filters;

/** Filter callback interface. */
public interface Callback<T> {
    void onEvent(T value);
}

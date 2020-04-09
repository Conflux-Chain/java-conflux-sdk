package org.cfx.abi.datatypes.generated;

import java.util.List;

import org.cfx.abi.datatypes.StaticArray;
import org.cfx.abi.datatypes.Type;

public class StaticArray13<T extends Type> extends StaticArray<T> {
    @Deprecated
    public StaticArray13(List<T> values) {
        super(13, values);
    }

    @Deprecated
    @SafeVarargs
    public StaticArray13(T... values) {
        super(13, values);
    }

    public StaticArray13(Class<T> type, List<T> values) {
        super(type, 13, values);
    }

    @SafeVarargs
    public StaticArray13(Class<T> type, T... values) {
        super(type, 13, values);
    }
}

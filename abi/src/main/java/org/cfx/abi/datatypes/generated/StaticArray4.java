package org.cfx.abi.datatypes.generated;

import java.util.List;

import org.cfx.abi.datatypes.StaticArray;
import org.cfx.abi.datatypes.Type;

public class StaticArray4<T extends Type> extends StaticArray<T> {
    @Deprecated
    public StaticArray4(List<T> values) {
        super(4, values);
    }

    @Deprecated
    @SafeVarargs
    public StaticArray4(T... values) {
        super(4, values);
    }

    public StaticArray4(Class<T> type, List<T> values) {
        super(type, 4, values);
    }

    @SafeVarargs
    public StaticArray4(Class<T> type, T... values) {
        super(type, 4, values);
    }
}

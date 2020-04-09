package org.cfx.abi.datatypes.generated;

import java.util.List;

import org.cfx.abi.datatypes.StaticArray;
import org.cfx.abi.datatypes.Type;

public class StaticArray19<T extends Type> extends StaticArray<T> {
    @Deprecated
    public StaticArray19(List<T> values) {
        super(19, values);
    }

    @Deprecated
    @SafeVarargs
    public StaticArray19(T... values) {
        super(19, values);
    }

    public StaticArray19(Class<T> type, List<T> values) {
        super(type, 19, values);
    }

    @SafeVarargs
    public StaticArray19(Class<T> type, T... values) {
        super(type, 19, values);
    }
}

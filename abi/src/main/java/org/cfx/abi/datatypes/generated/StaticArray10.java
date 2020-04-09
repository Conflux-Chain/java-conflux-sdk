package org.cfx.abi.datatypes.generated;

import java.util.List;
import org.cfx.abi.datatypes.StaticArray;
import org.cfx.abi.datatypes.Type;

public class StaticArray10<T extends Type> extends StaticArray<T> {
    @Deprecated
    public StaticArray10(List<T> values) {
        super(10, values);
    }

    @Deprecated
    @SafeVarargs
    public StaticArray10(T... values) {
        super(10, values);
    }

    public StaticArray10(Class<T> type, List<T> values) {
        super(type, 10, values);
    }

    @SafeVarargs
    public StaticArray10(Class<T> type, T... values) {
        super(type, 10, values);
    }
}

package org.cfx.abi.datatypes.generated;

import java.util.List;
import org.cfx.abi.datatypes.StaticArray;
import org.cfx.abi.datatypes.Type;

public class StaticArray2<T extends Type> extends StaticArray<T> {
    @Deprecated
    public StaticArray2(List<T> values) {
        super(2, values);
    }

    @Deprecated
    @SafeVarargs
    public StaticArray2(T... values) {
        super(2, values);
    }

    public StaticArray2(Class<T> type, List<T> values) {
        super(type, 2, values);
    }

    @SafeVarargs
    public StaticArray2(Class<T> type, T... values) {
        super(type, 2, values);
    }
}

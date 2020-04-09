package org.cfx.abi.datatypes.generated;

import java.util.List;
import org.cfx.abi.datatypes.StaticArray;
import org.cfx.abi.datatypes.Type;


public class StaticArray28<T extends Type> extends StaticArray<T> {
    @Deprecated
    public StaticArray28(List<T> values) {
        super(28, values);
    }

    @Deprecated
    @SafeVarargs
    public StaticArray28(T... values) {
        super(28, values);
    }

    public StaticArray28(Class<T> type, List<T> values) {
        super(type, 28, values);
    }

    @SafeVarargs
    public StaticArray28(Class<T> type, T... values) {
        super(type, 28, values);
    }
}

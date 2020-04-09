package org.cfx.abi.datatypes.generated;

import java.util.List;
import org.cfx.abi.datatypes.StaticArray;
import org.cfx.abi.datatypes.Type;

public class StaticArray7<T extends Type> extends StaticArray<T> {
    @Deprecated
    public StaticArray7(List<T> values) {
        super(7, values);
    }

    @Deprecated
    @SafeVarargs
    public StaticArray7(T... values) {
        super(7, values);
    }

    public StaticArray7(Class<T> type, List<T> values) {
        super(type, 7, values);
    }

    @SafeVarargs
    public StaticArray7(Class<T> type, T... values) {
        super(type, 7, values);
    }
}

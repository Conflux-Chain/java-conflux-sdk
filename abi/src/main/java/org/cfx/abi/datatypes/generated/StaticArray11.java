package org.cfx.abi.datatypes.generated;

import java.util.List;
import org.cfx.abi.datatypes.StaticArray;
import org.cfx.abi.datatypes.Type;

public class StaticArray11<T extends Type> extends StaticArray<T> {
    @Deprecated
    public StaticArray11(List<T> values) {
        super(11, values);
    }

    @Deprecated
    @SafeVarargs
    public StaticArray11(T... values) {
        super(11, values);
    }

    public StaticArray11(Class<T> type, List<T> values) {
        super(type, 11, values);
    }

    @SafeVarargs
    public StaticArray11(Class<T> type, T... values) {
        super(type, 11, values);
    }
}

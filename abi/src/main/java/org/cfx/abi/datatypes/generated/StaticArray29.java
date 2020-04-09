package org.cfx.abi.datatypes.generated;

import java.util.List;
import org.cfx.abi.datatypes.StaticArray;
import org.cfx.abi.datatypes.Type;

public class StaticArray29<T extends Type> extends StaticArray<T> {
    @Deprecated
    public StaticArray29(List<T> values) {
        super(29, values);
    }

    @Deprecated
    @SafeVarargs
    public StaticArray29(T... values) {
        super(29, values);
    }

    public StaticArray29(Class<T> type, List<T> values) {
        super(type, 29, values);
    }

    @SafeVarargs
    public StaticArray29(Class<T> type, T... values) {
        super(type, 29, values);
    }
}

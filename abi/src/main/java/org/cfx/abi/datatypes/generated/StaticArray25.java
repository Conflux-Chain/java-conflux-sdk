package org.cfx.abi.datatypes.generated;

import java.util.List;
import org.cfx.abi.datatypes.StaticArray;
import org.cfx.abi.datatypes.Type;

public class StaticArray25<T extends Type> extends StaticArray<T> {
    @Deprecated
    public StaticArray25(List<T> values) {
        super(25, values);
    }

    @Deprecated
    @SafeVarargs
    public StaticArray25(T... values) {
        super(25, values);
    }

    public StaticArray25(Class<T> type, List<T> values) {
        super(type, 25, values);
    }

    @SafeVarargs
    public StaticArray25(Class<T> type, T... values) {
        super(type, 25, values);
    }
}

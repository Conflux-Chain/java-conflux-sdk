package org.cfx.abi.datatypes.generated;

import java.util.List;
import org.cfx.abi.datatypes.StaticArray;
import org.cfx.abi.datatypes.Type;

public class StaticArray9<T extends Type> extends StaticArray<T> {
    @Deprecated
    public StaticArray9(List<T> values) {
        super(9, values);
    }

    @Deprecated
    @SafeVarargs
    public StaticArray9(T... values) {
        super(9, values);
    }

    public StaticArray9(Class<T> type, List<T> values) {
        super(type, 9, values);
    }

    @SafeVarargs
    public StaticArray9(Class<T> type, T... values) {
        super(type, 9, values);
    }
}

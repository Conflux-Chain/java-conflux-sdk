
package org.cfx.abi.datatypes.primitive;

import java.io.Serializable;
import java.util.Objects;

import org.cfx.abi.datatypes.Type;

public abstract class PrimitiveType<T extends Serializable & Comparable<T>> implements Type<T> {

    private final String type;
    private final T value;

    PrimitiveType(final T value) {
        this.type = getClass().getSimpleName().toLowerCase();
        this.value = value;
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public String getTypeAsString() {
        return type;
    }

    public abstract Type toSolidityType();

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PrimitiveType<?> that = (PrimitiveType<?>) o;
        return type.equals(that.type) && value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, value);
    }
}

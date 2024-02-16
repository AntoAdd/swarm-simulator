package it.unicam.cs.pa.swarmsimulator.model.robot;

import java.util.Objects;

public abstract class StateComponent<T> {
    private final T value;

    protected StateComponent(T value) {
        this.value = Objects.requireNonNull(value);
    }

    public T getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StateComponent<?> that = (StateComponent<?>) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}

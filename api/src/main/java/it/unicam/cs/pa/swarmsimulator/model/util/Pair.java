package it.unicam.cs.pa.swarmsimulator.model.util;

import java.util.Objects;

public record Pair<K, V>(K first, V second) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(first, pair.first) && Objects.equals(second, pair.second);
    }
}

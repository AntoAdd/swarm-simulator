package it.unicam.cs.pa.swarmsimulator.model;

/**
 * This interface represents a position in the environment.
 *
 * @param <C> the type of coordinates associated to this position.
 */
public interface Position<C> {
    /**
     * Returns the coordinates for this position.
     *
     * @return the coordinates for this position.
     */
    C getPositionCoordinates();
}

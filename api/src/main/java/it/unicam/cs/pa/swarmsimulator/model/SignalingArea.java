package it.unicam.cs.pa.swarmsimulator.model;

/**
 * A signaling area occupies a region in the environment and signals a condition to the robots that fly
 * over it.
 *
 * @param <C> the type of position that identifies the area center.
 */
public interface SignalingArea<C extends Position<C>> {
    /**
     * Returns the center position for this signaling area.
     *
     * @return the center position for this signaling area.
     */
    C getCenterPosition();

    /**
     * Returns the condition signaled by this area.
     *
     * @return the condition signaled by this area.
     */
    String getCondition();

    /**
     * Returns true if the given position is contained in this area, false otherwise.
     *
     * @param position the given position.
     * @return true if the given position is contained in this area, false otherwise.
     */
    boolean contains(C position);
}

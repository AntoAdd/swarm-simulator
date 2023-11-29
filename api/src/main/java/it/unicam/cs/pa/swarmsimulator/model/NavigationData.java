package it.unicam.cs.pa.swarmsimulator.model;

/**
 * This interface is used to define a navigation data: a navigation data is a component of a navigation state,
 * that contribute with the others to represent the movement or signaling behaviour of a robot.
 */
public interface NavigationData<V> {
    /**
     * Returns the value of this navigation data.
     *
     * @return the value of this navigation data.
     */
    V getValue();
}

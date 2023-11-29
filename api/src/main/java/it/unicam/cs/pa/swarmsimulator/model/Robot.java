package it.unicam.cs.pa.swarmsimulator.model;

/**
 * A Robot interface is used to represent a robot in the swarm. A robot collects and updates navigation data,
 * describing its movement in the space, after executing a command.
 *
 * @param <S> the type of navigation state of this robot.
 */
public interface Robot<S extends NavigationState> {
    /**
     * Returns the identifier for this robot.
     *
     * @return the identifier for this robot.
     */
    long getId();

    /**
     * Returns the current navigation state of this robot.
     *
     * @return the current navigation state of this robot.
     */
    S getNavigationState();

    /**
     * Updates the current navigation state of this robot with the new navigation state.
     * @param s the new navigation state.
     */
    void updateNavigationState(S s);
}

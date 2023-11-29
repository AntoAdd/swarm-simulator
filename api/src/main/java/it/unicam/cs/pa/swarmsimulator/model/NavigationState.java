package it.unicam.cs.pa.swarmsimulator.model;

import java.util.Map;

/**
 * A navigation state represents and collects navigation data describing the movement and signaling behaviour
 * of a robot in a particular moment in time.
 */
public interface NavigationState {
    /**
     * Returns all the navigation data describing this navigation state. Each navigation data is identified
     * by a string descriptor.
     *
     * @return all the navigation data describing this navigation state.
     */
    Map<String, ? extends NavigationData> getAllNavigationData();

    /**
     * Returns the corresponding navigation data identified by the given identifier.
     *
     * @param identifier the identifier used to retrieve the corresponding navigation data.
     * @return the corresponding navigation data identified by the given identifier.
     */
    NavigationData getNavigationDataIdentifiedBy(String identifier);
}

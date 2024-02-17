package it.unicam.cs.pa.swarmsimulator.model;

/**
 * This interface represents a simulator that is responsible for executing a simulation of the system for a given
 * amount of time.
 */
public interface Simulator {
    /**
     * Executes the simulation of the system for the given amount of time.
     *
     * @param dt the required time for the execution of a single robot command.
     * @param time the time amount for which the simulation has to run.
     */
    void simulate(double dt, double time);
}

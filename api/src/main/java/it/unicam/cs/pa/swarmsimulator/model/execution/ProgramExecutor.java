package it.unicam.cs.pa.swarmsimulator.model.execution;

import it.unicam.cs.pa.swarmsimulator.model.environment.Environment;
import it.unicam.cs.pa.swarmsimulator.model.environment.Position;
import it.unicam.cs.pa.swarmsimulator.model.robotstate.RobotState;

/**
 * A program executor is responsible for the execution of the robot program for all robots in the environment.
 */
public interface ProgramExecutor<S extends RobotState, C extends Position<C>> {
    /**
     * Perform a step forward in the execution: it does so executing the next command for each robot
     * in the environment.
     */
    void stepForward();

    /**
     * Returns the current environment reflecting the robot configuration at this time of execution.
     *
     * @return the current environment reflecting the robot configuration at this time of execution.
     */
    Environment<S, C> getCurrentConfiguration();
}

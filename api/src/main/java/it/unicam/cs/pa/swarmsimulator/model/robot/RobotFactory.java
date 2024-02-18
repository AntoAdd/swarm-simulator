package it.unicam.cs.pa.swarmsimulator.model.robot;

import it.unicam.cs.pa.swarmsimulator.model.robotstate.StandardState;

import java.util.List;

/**
 * A robot factory constructs robot instances of the appropriate type.
 *
 * @param <S> the type of robot state of the robots created.
 */
public interface RobotFactory<S extends StandardState> {
    /**
     * Returns a newly created robot.
     *
     * @return a newly created robot.
     */
    Robot<S> createRobot();

    /**
     * Returns a list of newly created robots.
     *
     * @param n the number of robots to create.
     * @return a list of newly created robots.
     */
    List<Robot<S>> createRobots(int n);
}

package it.unicam.cs.pa.swarmsimulator.model;

import it.unicam.cs.pa.swarmsimulator.model.area.SignalingArea;
import it.unicam.cs.pa.swarmsimulator.model.robot.Robot;
import it.unicam.cs.pa.swarmsimulator.model.robotstate.RobotState;


import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This interface represents the environment in which the swarm moves.
 *
 * The environment registers the signaling areas and robot positions and keeps track of the evolving
 * robot positions in time.
 *
 * @param <S> the type of the robots' navigation state.
 * @param <C> the type of positions in this environment.
 */
public interface Environment<S extends RobotState, C extends Position<C>> {
    Robot<S> getRobot(long id);

    Set<Robot<S>> getRobots();
    /**
     * Returns the position of the given robot in the environment.
     *
     * @param robotId the given robot.
     * @return the position of the given robot in the environment.
     */
    C getRobotPosition(Robot<S> robotId);

    /**
     * Returns the current positions of all the robots in the environment.
     *
     * @return the current positions of all the robots in the environment.
     */
    List<C> getRobotPositions();

    /**
     * Returns all the robots that are signaling a given condition and are in range from the given robot.
     *
     * @param robot the given robot.
     * @param condition the condition that the given robot wants to perceive.
     * @param distance the distance used to calculate the range of perception of the robot.
     * @return all the robots that are signaling a given condition and are in range from the given robot.
     */
    List<Robot<S>> getSignalingRobotsInRange(Robot<S> robot, String condition, double distance);

    List<C> getPositionsOf(List<Robot<S>> robots);

    /**
     * Returns the position of the given area's center in the environment.
     *
     * @param area the given area.
     * @return the position of the given area's center in the environment.
     */
    C getSignalingAreaPosition(SignalingArea<C> area);

    /**
     * Returns the positions of the signaling areas' centers in the environment.
     *
     * @return the positions of the signaling areas' centers in the environment.
     */
    List<C> getSignalingAreaPositions();

    /**
     * Returns the robots that are present in the given signaling area.
     * If no robot is present, a <code>Collection.empty()</code> is returned.
     *
     * @param area the given signaling area.
     * @return the robots that are present in the given signaling area.
     */
    List<Robot<S>> getRobotsInArea(SignalingArea<C> area);

    /**
     * Returns the area that the given robot is occupying. If the robot is not present in any areas of
     * the environment, an <code>Optional.empty()</code> is returned.
     *
     * @param robot the given robot.
     * @return the area that the given robot is occupying, if exists.
     */
    List<SignalingArea<C>> getAreasOccupiedBy(Robot<S> robot);

    /**
     * Updates the state of the given robot with a new given state.
     *
     * @param robot the given robot.
     * @param state the new state used to update the robot.
     */
    void updateRobotState(Robot<S> robot, S state);

    /**
     * Updates this environment with the new robot positions.
     *
     * @param updatedConfiguration the new configuration of the robots in this environment.
     * @return the evolved environment.
     */
    Environment<S, C> evolve(Map<Robot<S>, C> updatedConfiguration);
}

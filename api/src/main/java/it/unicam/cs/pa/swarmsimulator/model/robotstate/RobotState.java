package it.unicam.cs.pa.swarmsimulator.model.robotstate;

/**
 * This interface represents the navigation state of a robot.
 */
public interface RobotState {
    /**
     * Returns true if this robot is currently moving, false otherwise.
     *
     * @return true if this robot is currently moving, false otherwise.
     */
    boolean isMoving();

    /**
     * Returns true if this robot is currently signaling a condition, false otherwise.
     *
     * @return true if this robot is currently signaling a condition, false otherwise.
     */
    boolean isSignaling();
}

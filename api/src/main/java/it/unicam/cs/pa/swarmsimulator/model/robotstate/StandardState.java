package it.unicam.cs.pa.swarmsimulator.model.robotstate;

import it.unicam.cs.pa.swarmsimulator.model.robot.Direction;
import it.unicam.cs.pa.swarmsimulator.model.robot.SignalingCondition;
import it.unicam.cs.pa.swarmsimulator.model.robot.Speed;
import it.unicam.cs.pa.swarmsimulator.model.util.Pair;

import java.util.Objects;

/**
 * This class represent the standard robot state.
 *
 * It is constituted of three components: speed, signaled condition and direction.
 * The empty string ("") stands for no condition signaled by the robot at the current moment in time.
 */
public class StandardState implements RobotState{
    private final Speed speed;
    private final Direction direction;
    private final SignalingCondition condition;

    public StandardState(Speed speed, Direction direction, SignalingCondition condition) {
        this.speed = Objects.requireNonNull(speed);
        this.direction = Objects.requireNonNull(direction);
        this.condition = Objects.requireNonNull(condition);
    }

    /**
     * This constructor is used to create the initial state for the robots.
     */
    public static StandardState getInitialState() {
        return new StandardState(new Speed(0.0), new Direction(0.0), new SignalingCondition(""));
    }

    public Speed getSpeed() {
        return speed;
    }

    public Direction getDirection() {
        return direction;
    }

    public SignalingCondition getCondition() {
        return condition;
    }

    @Override
    public boolean isMoving() {
        return speed.getValue() > 0.0;
    }

    @Override
    public boolean isSignaling() {
        return !condition.getValue().equals("");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StandardState that = (StandardState) o;
        return Objects.equals(speed, that.speed) && Objects.equals(direction, that.direction) && Objects.equals(condition, that.condition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(speed, direction, condition);
    }
}

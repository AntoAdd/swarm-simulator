package it.unicam.cs.pa.swarmsimulator.model;

import it.unicam.cs.pa.swarmsimulator.model.util.Pair;

import java.util.Objects;

/**
 * This class represent the standard robot state.
 *
 * It is constituted of three components: speed, signaled condition and direction.
 * The empty string ("") stands for no condition signaled by the robot at the current moment in time.
 */
public class StandardState implements RobotState{
    private final Double speed;
    private final Pair<Double, Double> direction;
    private final String condition;

    public StandardState(Double speed, Pair<Double, Double> direction, String condition) {
        this.speed = Objects.requireNonNull(speed);
        this.direction = Objects.requireNonNull(direction);
        this.condition = Objects.requireNonNull(condition);
    }

    /**
     * This constructor is used to create the initial state for the robots.
     */
    public StandardState() {
        this(0.0, new Pair<>(0.0, 0.0), "");
    }

    public Double getSpeed() {
        return speed;
    }

    public Pair<Double, Double> getDirection() {
        return direction;
    }

    public String getCondition() {
        return condition;
    }

    @Override
    public boolean isMoving() {
        return speed > 0.0;
    }

    @Override
    public boolean isSignaling() {
        return !condition.equals("");
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

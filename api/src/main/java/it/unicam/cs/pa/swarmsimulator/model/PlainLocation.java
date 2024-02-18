package it.unicam.cs.pa.swarmsimulator.model;

import it.unicam.cs.pa.swarmsimulator.model.robot.Direction;

import java.util.Random;

/**
 * Represents a location in the infinite plain space.
 *
 * @param x the x coordinate in the plain.
 * @param y the y coordinate in the plain.
 */
public record PlainLocation(double x, double y) implements Position<PlainLocation> {
    /**
     * Constructs a random position.
     */
    public PlainLocation() {
        this(getRandomValue(), getRandomValue());
    }

    private static double getRandomValue() {
        Random r = new Random();
        return Double.MAX_VALUE * r.nextDouble() * (r.nextBoolean() ? 1 : -1);
    }

    public double distanceFrom(PlainLocation location){
        return Math.sqrt(Math.pow(location.x() - x, 2) + Math.pow(location.y() - y, 2));
    }

    public Direction directionTo(PlainLocation location){
        double angle = Math.toDegrees(Math.atan2(location.y() - y, location.x() - x));
        return new Direction(angle);
    }

    @Override
    public PlainLocation getPositionCoordinates() {
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlainLocation that = (PlainLocation) o;
        return Double.compare(that.x, x) == 0 && Double.compare(that.y, y) == 0;
    }

    @Override
    public String toString() {
        return "PlainLocation{" +
            "x=" + x +
            ", y=" + y +
            '}';
    }
}

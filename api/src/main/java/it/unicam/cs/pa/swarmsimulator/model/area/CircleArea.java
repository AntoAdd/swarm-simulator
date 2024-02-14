package it.unicam.cs.pa.swarmsimulator.model.area;

import it.unicam.cs.pa.swarmsimulator.model.PlainLocation;

import java.util.Objects;

public class CircleArea implements SignalingArea<PlainLocation>{
    private final PlainLocation centerPosition;
    private final String signaledCondition;
    private final double radius;

    public CircleArea(PlainLocation centerPosition, String signaledCondition, double radius) {
        this.centerPosition = Objects.requireNonNull(centerPosition);
        this.signaledCondition = Objects.requireNonNull(signaledCondition);
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public PlainLocation getCenterPosition() {
        return centerPosition;
    }

    @Override
    public String getCondition() {
        return signaledCondition;
    }

    @Override
    public boolean contains(PlainLocation position) {
        return centerPosition.distanceFrom(position) < radius;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CircleArea that = (CircleArea) o;
        return Double.compare(that.radius, radius) == 0 && Objects.equals(centerPosition, that.centerPosition) && Objects.equals(signaledCondition, that.signaledCondition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(centerPosition, signaledCondition, radius);
    }
}

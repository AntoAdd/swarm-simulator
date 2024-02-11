package it.unicam.cs.pa.swarmsimulator.model;

import java.util.Objects;

public class RectangleArea implements SignalingArea<PlainLocation>{
    private final PlainLocation centerPosition;
    private final String signaledCondition;
    private final double width;
    private final double height;

    public RectangleArea(PlainLocation centerPosition, String signaledCondition, double width, double height) {
        this.centerPosition = Objects.requireNonNull(centerPosition);
        this.signaledCondition = Objects.requireNonNull(signaledCondition);
        this.width = width;
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
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
        return Math.abs(position.x() - centerPosition.x()) < width/2
            && Math.abs(position.y() - centerPosition.y()) < height/2;
    }
}

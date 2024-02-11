package it.unicam.cs.pa.swarmsimulator.model;

/**
 * Represents a location in the infinite plain space.
 *
 * @param x the x coordinate in the plain.
 * @param y the y coordinate in the plain.
 */
public record PlainLocation(double x, double y) implements Position<PlainLocation> {

    public double distanceFrom(PlainLocation location){
        return Math.sqrt(Math.pow(location.x() - x, 2) + Math.pow(location.y() - y, 2));
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

package it.unicam.cs.pa.swarmsimulator.test;

import it.unicam.cs.pa.swarmsimulator.model.area.CircleArea;
import it.unicam.cs.pa.swarmsimulator.model.area.RectangleArea;
import it.unicam.cs.pa.swarmsimulator.model.area.SignalingArea;
import it.unicam.cs.pa.swarmsimulator.model.environment.PlainLocation;
import it.unicam.cs.pa.swarmsimulator.model.environment.Position;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SignalingAreaTest {
    @Test
    public void circleAreaShouldContainPositions(){
        Position<PlainLocation> centerPosition = new PlainLocation(0.0, 0.0);
        SignalingArea<PlainLocation> circleArea = new CircleArea(
            centerPosition.getPositionCoordinates(), "a", 5.0
        );

        List<Position<PlainLocation>> positionsContained = List.of(
            new PlainLocation(0.0, 2.5),
            new PlainLocation(0.0, -2.5),
            new PlainLocation(3.5, 0.0),
            new PlainLocation(-3.5, 0.0),
            new PlainLocation(1.5, 1.5),
            new PlainLocation(-1.5, 1.5),
            new PlainLocation(1.5, -1.5),
            new PlainLocation(-1.5, -1.5)
        );

        for (Position<PlainLocation> p:
             positionsContained) {
            assertTrue(circleArea.contains(p.getPositionCoordinates()));
        }
    }

    @Test
    public void circleAreaShouldNotContainPositions(){
        Position<PlainLocation> centerPosition = new PlainLocation(0.0, 0.0);
        SignalingArea<PlainLocation> circleArea = new CircleArea(
            centerPosition.getPositionCoordinates(), "a", 5.0
        );

        List<Position<PlainLocation>> positionsNotContained = List.of(
            new PlainLocation(0.0, 6.0),
            new PlainLocation(0.0, -6.0),
            new PlainLocation(6.5, 0.0),
            new PlainLocation(-6.5, 0.0),
            new PlainLocation(1.5, 7.5),
            new PlainLocation(-1.5, 7.5),
            new PlainLocation(1.5, -7.5),
            new PlainLocation(-1.5, -7.5)
        );

        for (Position<PlainLocation> p:
             positionsNotContained) {
            assertFalse(circleArea.contains(p.getPositionCoordinates()));
        }
    }

    @Test
    public void rectangleAreaShouldContainPositions(){
        Position<PlainLocation> centerPosition = new PlainLocation(-1.0, 0.0);
        SignalingArea<PlainLocation> rectangleArea = new RectangleArea(
            centerPosition.getPositionCoordinates(), "b", 6.0, 4.0
        );

        List<Position<PlainLocation>> positionsContained = List.of(
            new PlainLocation(1.0, 1.0),
            new PlainLocation(-3.0, 1.0),
            new PlainLocation(-3.0, -1.0),
            new PlainLocation(1.0, -1.0)
        );

        for (Position<PlainLocation> p:
             positionsContained) {
            assertTrue(rectangleArea.contains(p.getPositionCoordinates()));
        }
    }

    @Test
    public void rectangleAreaShouldNotContainPositions(){
        Position<PlainLocation> centerPosition = new PlainLocation(-1.0, 0.0);
        SignalingArea<PlainLocation> rectangleArea = new RectangleArea(
            centerPosition.getPositionCoordinates(), "b", 6.0, 4.0
        );

        List<Position<PlainLocation>> positionsNotContained = List.of(
            new PlainLocation(2.5, 1.0),
            new PlainLocation(-3.0, 3.0),
            new PlainLocation(-3.0, -3.0),
            new PlainLocation(2.5, -1.0)
        );

        for (Position<PlainLocation> p:
            positionsNotContained) {
            assertFalse(rectangleArea.contains(p.getPositionCoordinates()));
        }
    }

    @Test
    public void positionShouldBeContainedInBothAreas(){
        Position<PlainLocation> circleCenter = new PlainLocation(0.0, 0.0);
        Position<PlainLocation> rectangleCenter = new PlainLocation(-1.0, 0.0);

        SignalingArea<PlainLocation> rectangleArea = new RectangleArea(
            rectangleCenter.getPositionCoordinates(), "b", 6.0, 4.0
        );

        SignalingArea<PlainLocation> circleArea = new CircleArea(
            circleCenter.getPositionCoordinates(), "a", 1.0
        );

        Position<PlainLocation> position = new PlainLocation(0.5, 0.5);

        assertTrue(rectangleArea.contains(position.getPositionCoordinates())
            && circleArea.contains(position.getPositionCoordinates()));
    }

    @Test
    public void positionShouldNotBeContainedInBothAreas(){
        Position<PlainLocation> circleCenter = new PlainLocation(0.0, 0.0);
        Position<PlainLocation> rectangleCenter = new PlainLocation(-1.0, 0.0);

        SignalingArea<PlainLocation> rectangleArea = new RectangleArea(
            rectangleCenter.getPositionCoordinates(), "b", 6.0, 4.0
        );

        SignalingArea<PlainLocation> circleArea = new CircleArea(
            circleCenter.getPositionCoordinates(), "a", 1.0
        );

        Position<PlainLocation> position = new PlainLocation(-2.0, -1.0);

        assertFalse(rectangleArea.contains(position.getPositionCoordinates())
            && circleArea.contains(position.getPositionCoordinates()));
    }
}

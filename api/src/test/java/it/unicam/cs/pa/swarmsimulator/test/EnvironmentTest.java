package it.unicam.cs.pa.swarmsimulator.test;

import it.unicam.cs.pa.swarmsimulator.model.environment.Environment;
import it.unicam.cs.pa.swarmsimulator.model.environment.PlainEnvironment;
import it.unicam.cs.pa.swarmsimulator.model.environment.PlainLocation;
import it.unicam.cs.pa.swarmsimulator.model.area.CircleArea;
import it.unicam.cs.pa.swarmsimulator.model.area.RectangleArea;
import it.unicam.cs.pa.swarmsimulator.model.area.SignalingArea;
import it.unicam.cs.pa.swarmsimulator.model.robot.*;
import it.unicam.cs.pa.swarmsimulator.model.robotstate.StandardState;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EnvironmentTest {
    private static Environment<StandardState, PlainLocation> environment;

    @BeforeAll
    static void initialize(){
        Map<Robot<StandardState>, PlainLocation> robotsConfiguration = new HashMap<>();
        List<SignalingArea<PlainLocation>> areas = new ArrayList<>();

        robotsConfiguration.put(new StandardRobot(1), new PlainLocation(1, 2));
        robotsConfiguration.put(new StandardRobot(2), new PlainLocation(5, 6));
        robotsConfiguration.put(new StandardRobot(3), new PlainLocation(3, 4));
        robotsConfiguration.put(new StandardRobot(4), new PlainLocation(-4, -7));

        areas.add(new CircleArea(new PlainLocation(3, 3), "a", 2));
        areas.add(new RectangleArea(new PlainLocation(6, 6), "b", 3, 2));

        environment = new PlainEnvironment(robotsConfiguration, areas);
    }

    @Test
    @Order(1)
    public void robotPositionShouldBeCorrect(){
        Robot<StandardState> r = environment.getRobot(1);
        assertEquals(new PlainLocation(1, 2), environment.getRobotPosition(r));
    }

    @Test
    @Order(2)
    public void robotsSignalingInRangeShouldBeReturned(){
        environment.getRobot(2).updateNavigationState(
            new StandardState(new Speed(0.0), new Direction(0.0), new SignalingCondition("a"))
        );
        environment.getRobot(3).updateNavigationState(
            new StandardState(new Speed(0.0), new Direction(0.0), new SignalingCondition("a"))
        );

        List<Robot<StandardState>> robotsInRange = environment.getSignalingRobotsInRange(
            environment.getRobot(1), "a", 6.0
        );

        assertEquals(2, robotsInRange.size());
        assertTrue(robotsInRange.contains(environment.getRobot(2)));
        assertTrue(robotsInRange.contains(environment.getRobot(3)));
    }

    @Test
    @Order(3)
    public void noSignalingRobotInRangeShouldBePresent(){
        List<Robot<StandardState>> robotsInRangeForBCondition = environment.getSignalingRobotsInRange(
            environment.getRobot(1), "b", 22.5
        );

        List<Robot<StandardState>> robotsInRangeForACondition = environment.getSignalingRobotsInRange(
            environment.getRobot(1), "a", 2.5
        );

        assertEquals(0, robotsInRangeForBCondition.size());
        assertEquals(0, robotsInRangeForACondition.size());
    }

    @Test
    @Order(4)
    public void areasShouldContainCorrectRobots(){
        List<Robot<StandardState>> robotsInCircleArea = environment.getRobotsInArea(new CircleArea(
            new PlainLocation(3, 3), "a", 2
        ));

        List<Robot<StandardState>> robotsInRectangleArea = environment.getRobotsInArea(
            new RectangleArea(new PlainLocation(6, 6), "b", 3, 2)
        );

        assertEquals(1, robotsInCircleArea.size());
        assertEquals(1, robotsInRectangleArea.size());
        assertTrue(robotsInCircleArea.contains(environment.getRobot(3)));
        assertTrue(robotsInRectangleArea.contains(environment.getRobot(2)));
    }

    @Test
    @Order(5)
    public void robotsShouldOccupyCorrectAreas(){
        List<SignalingArea<PlainLocation>> areasForRobot1 = environment.getAreasOccupiedBy(
            environment.getRobot(1)
        );

        List<SignalingArea<PlainLocation>> areasForRobot2 = environment.getAreasOccupiedBy(
            environment.getRobot(2)
        );

        List<SignalingArea<PlainLocation>> areasForRobot3 = environment.getAreasOccupiedBy(
            environment.getRobot(3)
        );

        List<SignalingArea<PlainLocation>> areasForRobot4 = environment.getAreasOccupiedBy(
            environment.getRobot(4)
        );

        assertEquals(1, areasForRobot2.size());
        assertEquals(1, areasForRobot3.size());
        assertEquals(0, areasForRobot1.size());
        assertEquals(0, areasForRobot4.size());
    }
}

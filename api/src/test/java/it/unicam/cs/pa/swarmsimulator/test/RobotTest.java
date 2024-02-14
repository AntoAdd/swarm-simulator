package it.unicam.cs.pa.swarmsimulator.test;

import it.unicam.cs.pa.swarmsimulator.model.robot.Robot;
import it.unicam.cs.pa.swarmsimulator.model.robot.StandardRobot;
import it.unicam.cs.pa.swarmsimulator.model.robotstate.StandardState;
import it.unicam.cs.pa.swarmsimulator.model.util.Pair;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RobotTest {
    @Test
    public void shouldHaveCorrectInitialState(){
        Robot<StandardState> r = new StandardRobot(1);
        StandardState initialState = new StandardState();

        assertEquals(initialState, r.getNavigationState());
    }

    @Test
    public void robotsShouldBeEqual(){
        Robot<StandardState> r = new StandardRobot(1);
        Robot<StandardState> t = new StandardRobot(1);

        assertEquals(r, t);
    }

    @Test
    public void stateShouldBeUpdatedCorrectly(){
        Robot<StandardState> r = new StandardRobot(1);
        StandardState updateState = new StandardState(22.5, new Pair<>(0.0, 5.2), "a");
        r.updateNavigationState(updateState);

        assertEquals(updateState, r.getNavigationState());
    }

    @Test
    public void robotShouldBeStartSignaling(){
        Robot<StandardState> r = new StandardRobot(1);
        StandardState initialState = r.getNavigationState();

        StandardState updateState = new StandardState(
            initialState.getSpeed(),
            initialState.getDirection(),
            "a"
        );

        r.updateNavigationState(updateState);

        assertTrue(r.getNavigationState().isSignaling());
    }

    @Test
    public void robotShouldStartMoving(){
        Robot<StandardState> r = new StandardRobot(1);
        StandardState initialState = r.getNavigationState();

        StandardState updateState = new StandardState(
            55.7,
            initialState.getDirection(),
            initialState.getCondition()
        );

        r.updateNavigationState(updateState);

        assertTrue(r.getNavigationState().isMoving());
    }
}

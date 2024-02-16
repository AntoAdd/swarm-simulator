package it.unicam.cs.pa.swarmsimulator.test;

import it.unicam.cs.pa.swarmsimulator.model.robot.*;
import it.unicam.cs.pa.swarmsimulator.model.robotstate.StandardState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RobotTest {
    @Test
    public void shouldHaveCorrectInitialState(){
        Robot<StandardState> r = new StandardRobot(1);
        StandardState initialState = StandardState.getInitialState();

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
        StandardState updateState = new StandardState(
            new Speed(22.5), new Direction(15.0), new SignalingCondition("a")
        );
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
            new SignalingCondition("a")
        );

        r.updateNavigationState(updateState);

        assertTrue(r.getNavigationState().isSignaling());
    }

    @Test
    public void robotShouldStartMoving(){
        Robot<StandardState> r = new StandardRobot(1);
        StandardState initialState = r.getNavigationState();

        StandardState updateState = new StandardState(
            new Speed(55.7),
            initialState.getDirection(),
            initialState.getCondition()
        );

        r.updateNavigationState(updateState);

        assertTrue(r.getNavigationState().isMoving());
    }
}

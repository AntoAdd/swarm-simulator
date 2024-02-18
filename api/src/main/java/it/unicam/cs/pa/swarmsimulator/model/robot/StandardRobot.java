package it.unicam.cs.pa.swarmsimulator.model.robot;

import it.unicam.cs.pa.swarmsimulator.model.robotstate.StandardState;

public class StandardRobot extends AbstractRobot<StandardState>{
    public StandardRobot(long id) {
        super(id);
        updateNavigationState(StandardState.getInitialState());
    }

    @Override
    public String toString() {
        return "StandardRobot: " + super.getId() + ", " + super.getNavigationState();
    }
}

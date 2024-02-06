package it.unicam.cs.pa.swarmsimulator.model;

public class StandardRobot extends AbstractRobot<StandardState>{
    public StandardRobot(long id) {
        super(id);
        updateNavigationState(new StandardState());
    }
}

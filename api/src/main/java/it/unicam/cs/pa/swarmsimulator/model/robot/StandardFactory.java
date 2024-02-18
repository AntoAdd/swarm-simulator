package it.unicam.cs.pa.swarmsimulator.model.robot;

import it.unicam.cs.pa.swarmsimulator.model.robotstate.StandardState;

import java.util.ArrayList;
import java.util.List;

public class StandardFactory implements RobotFactory<StandardState> {
    private static long id = 0;

    @Override
    public Robot<StandardState> createRobot() {
        return new StandardRobot(id++);
    }

    @Override
    public List<Robot<StandardState>> createRobots(int n) {
        List<Robot<StandardState>> createdRobots = new ArrayList<>();
        for (int i = 0; i < n; i++)
            createdRobots.add(createRobot());
        return createdRobots;
    }
}

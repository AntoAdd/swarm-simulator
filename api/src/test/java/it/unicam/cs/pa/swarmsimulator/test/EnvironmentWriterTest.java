package it.unicam.cs.pa.swarmsimulator.test;

import it.unicam.cs.pa.swarmsimulator.io.EnvironmentWriter;
import it.unicam.cs.pa.swarmsimulator.io.StandardWriter;
import it.unicam.cs.pa.swarmsimulator.model.Environment;
import it.unicam.cs.pa.swarmsimulator.model.PlainEnvironment;
import it.unicam.cs.pa.swarmsimulator.model.PlainLocation;
import it.unicam.cs.pa.swarmsimulator.model.area.CircleArea;
import it.unicam.cs.pa.swarmsimulator.model.area.RectangleArea;
import it.unicam.cs.pa.swarmsimulator.model.area.SignalingArea;
import it.unicam.cs.pa.swarmsimulator.model.robot.Robot;
import it.unicam.cs.pa.swarmsimulator.model.robot.StandardRobot;
import it.unicam.cs.pa.swarmsimulator.model.robotstate.StandardState;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnvironmentWriterTest {
    private static Environment<StandardState, PlainLocation> environment;

    @BeforeAll
    static void initializeEnvironment(){
        Map<Robot<StandardState>, PlainLocation> robots = new HashMap<>();

        robots.put(new StandardRobot(1), new PlainLocation(1, 2));
        robots.put(new StandardRobot(2), new PlainLocation(6, 2));
        robots.put(new StandardRobot(3), new PlainLocation(11, 2));

        List<SignalingArea<PlainLocation>> areas = new ArrayList<>();
        areas.add(new CircleArea(new PlainLocation(3, 0), "f", 1.0));
        areas.add(new RectangleArea(new PlainLocation(-3, -3), "b" , 4, 2));

        environment = new PlainEnvironment(robots, areas);
    }

    @Test
    public void environmentShouldBeWroteToFile() throws IOException {
        EnvironmentWriter<StandardState, PlainLocation> writer = new StandardWriter();
        File file = new File("src/test/resources/wroteEnvironment.txt");
        writer.writeTo(file, environment);

        Map<Robot<StandardState>, PlainLocation> evolvedMap = new HashMap<>();

        evolvedMap.put(new StandardRobot(1), new PlainLocation(1, 2));
        evolvedMap.put(new StandardRobot(2), new PlainLocation(6, 2));
        evolvedMap.put(new StandardRobot(3), new PlainLocation(11, 2));

        Environment<StandardState, PlainLocation> evolvedEnvironment = environment.evolve(evolvedMap);

        writer.writeTo(file, evolvedEnvironment);
    }
}

package it.unicam.cs.pa.swarmsimulator.test;

import it.unicam.cs.pa.swarmsimulator.model.area.CircleArea;
import it.unicam.cs.pa.swarmsimulator.model.area.RectangleArea;
import it.unicam.cs.pa.swarmsimulator.model.area.SignalingArea;
import it.unicam.cs.pa.swarmsimulator.model.commands.Continue;
import it.unicam.cs.pa.swarmsimulator.model.commands.Move;
import it.unicam.cs.pa.swarmsimulator.model.commands.RobotCommand;
import it.unicam.cs.pa.swarmsimulator.model.environment.Environment;
import it.unicam.cs.pa.swarmsimulator.model.environment.PlainEnvironment;
import it.unicam.cs.pa.swarmsimulator.model.environment.PlainLocation;
import it.unicam.cs.pa.swarmsimulator.model.execution.ProgramExecutor;
import it.unicam.cs.pa.swarmsimulator.model.execution.StandardProgramExecutor;
import it.unicam.cs.pa.swarmsimulator.model.robot.Robot;
import it.unicam.cs.pa.swarmsimulator.model.robot.Speed;
import it.unicam.cs.pa.swarmsimulator.model.robot.StandardRobot;
import it.unicam.cs.pa.swarmsimulator.model.robotstate.StandardState;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProgramExecutorTest {
    private static ProgramExecutor<StandardState, PlainLocation> executor;

    @BeforeAll
    public static void initialization(){
        Map<Robot<StandardState>, PlainLocation> robots = new HashMap<>();

        robots.put(new StandardRobot(1), new PlainLocation(1, 2));
        robots.put(new StandardRobot(2), new PlainLocation(6, 2));
        robots.put(new StandardRobot(3), new PlainLocation(11, 2));

        List<SignalingArea<PlainLocation>> areas = new ArrayList<>();
        areas.add(new CircleArea(new PlainLocation(3, 0), "f", 1.0));
        areas.add(new RectangleArea(new PlainLocation(-3, -3), "b" , 4, 2));

        List<RobotCommand> program = List.of(new Move(new PlainLocation(14, 2), new Speed(2.0)), new Continue(3));

        executor = new StandardProgramExecutor(program, new PlainEnvironment(robots, areas));
    }

    @Test
    public void shouldExecuteStepForwardCorrectly(){
        Environment<StandardState, PlainLocation> evolvedEnv;
        executor.stepForward();
        executor.stepForward();

        evolvedEnv = executor.getCurrentConfiguration();

        assertEquals(new PlainLocation(3, 2), evolvedEnv.getRobotPosition(evolvedEnv.getRobot(1)));
        assertEquals(new PlainLocation(8, 2), evolvedEnv.getRobotPosition(evolvedEnv.getRobot(2)));
        assertEquals(new PlainLocation(13, 2), evolvedEnv.getRobotPosition(evolvedEnv.getRobot(3)));

        executor.stepForward();
        executor.stepForward();

        evolvedEnv = executor.getCurrentConfiguration();

        assertEquals(new PlainLocation(7, 2), evolvedEnv.getRobotPosition(evolvedEnv.getRobot(1)));
        assertEquals(new PlainLocation(12, 2), evolvedEnv.getRobotPosition(evolvedEnv.getRobot(2)));
        assertEquals(new PlainLocation(17, 2), evolvedEnv.getRobotPosition(evolvedEnv.getRobot(3)));

        executor.stepForward();
    }
}

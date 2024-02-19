package it.unicam.cs.pa.swarmsimulator.test;

import it.unicam.cs.pa.swarmsimulator.model.area.CircleArea;
import it.unicam.cs.pa.swarmsimulator.model.area.RectangleArea;
import it.unicam.cs.pa.swarmsimulator.model.area.SignalingArea;
import it.unicam.cs.pa.swarmsimulator.model.commands.*;
import it.unicam.cs.pa.swarmsimulator.model.environment.Environment;
import it.unicam.cs.pa.swarmsimulator.model.environment.PlainEnvironment;
import it.unicam.cs.pa.swarmsimulator.model.environment.PlainLocation;
import it.unicam.cs.pa.swarmsimulator.model.execution.CommandEvaluator;
import it.unicam.cs.pa.swarmsimulator.model.execution.StandardEvaluator;
import it.unicam.cs.pa.swarmsimulator.model.robot.*;
import it.unicam.cs.pa.swarmsimulator.model.robotstate.StandardState;
import it.unicam.cs.pa.swarmsimulator.model.util.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandEvaluatorTest {
    private static CommandEvaluator<StandardState, PlainLocation> evaluator;

    @BeforeEach
    public void setEvaluator(){
        Environment<StandardState, PlainLocation> evaluationEnv = createEnvironment();
        evaluator = new StandardEvaluator(evaluationEnv);
    }

    private static Environment<StandardState, PlainLocation> createEnvironment() {
        Map<Robot<StandardState>, PlainLocation> robots = new HashMap<>();
        robots.put(new StandardRobot(1), new PlainLocation(0, 0));
        robots.put(new StandardRobot(2), new PlainLocation(2, 4));
        robots.put(new StandardRobot(3), new PlainLocation(-2, 4));

        List<SignalingArea<PlainLocation>> areas = new ArrayList<>();
        areas.add(new CircleArea(new PlainLocation(3, 0), "f", 1.0));
        areas.add(new RectangleArea(new PlainLocation(-3, -3), "b" , 4, 2));

        return new PlainEnvironment(robots, areas);
    }

    @Test
    public void moveShouldReturnCorrectStateAndPosition(){
        Robot<StandardState> r = evaluator.getEnvironment().getRobot(1);
        RobotCommand move = new Move(new PlainLocation(-2, -2), new Speed(5.0));
        PlainLocation robotPosition = evaluator.getEnvironment().getRobotPosition(r);

        Pair<StandardState, PlainLocation> result = evaluator.evaluate(r, move);

        assertEquals(-135.0, result.getFirst().getDirection().getValue());
        assertEquals(5.0, result.getFirst().getSpeed().getValue());
        assertEquals(robotPosition, result.getSecond());
    }

    @Test
    public void continueShouldReturnCorrectPosition(){
        Robot<StandardState> r = evaluator.getEnvironment().getRobot(1);

        RobotCommand up = new Move(new PlainLocation(0, 1), new Speed(5.0));
        RobotCommand down = new Move(new PlainLocation(0, -1), new Speed(5.0));
        RobotCommand right = new Move(new PlainLocation(1, 0), new Speed(5.0));
        RobotCommand left = new Move(new PlainLocation(-1, 0), new Speed(5.0));

        List<RobotCommand> moves = List.of(up, down, right, left);
        List<PlainLocation> resultPositions = new ArrayList<>();

        for (RobotCommand m : moves) {
            Pair<StandardState, PlainLocation> moveResult = evaluator.evaluate(r, m);
            evaluator.getEnvironment().updateRobotState(r, moveResult.getFirst());
            resultPositions.add(evaluator.evaluate(r, new Continue(1)).getSecond());
        }

        assertEquals(new PlainLocation(0, 5), resultPositions.get(0));
        assertEquals(new PlainLocation(0, -5), resultPositions.get(1));
        assertEquals(new PlainLocation(5, 0), resultPositions.get(2));
        assertEquals(new PlainLocation(-5, 0), resultPositions.get(3));
    }

    @Test
    public void shouldReturnCorrectDirectionToFollow(){
        Robot<StandardState> following = evaluator.getEnvironment().getRobot(1);
        Robot<StandardState> followed1 = evaluator.getEnvironment().getRobot(2);
        Robot<StandardState> followed2 = evaluator.getEnvironment().getRobot(3);

        RobotCommand c = new Signal(new SignalingCondition("a"));

        evaluator.getEnvironment().updateRobotState(followed1, evaluator.evaluate(followed1, c).getFirst());
        evaluator.getEnvironment().updateRobotState(followed2, evaluator.evaluate(followed2, c).getFirst());

        StandardState state = evaluator.evaluate(
            following,
            new Follow(new SignalingCondition("a"), 10.5, new Speed(5.0))
        ).getFirst();

        assertEquals(90.0, state.getDirection().getValue());
        assertEquals(5.0, state.getSpeed().getValue());
    }
}

package it.unicam.cs.pa.swarmsimulator.io;

import it.unicam.cs.pa.swarmsimulator.model.Environment;
import it.unicam.cs.pa.swarmsimulator.model.PlainEnvironment;
import it.unicam.cs.pa.swarmsimulator.model.PlainLocation;
import it.unicam.cs.pa.swarmsimulator.model.area.CircleArea;
import it.unicam.cs.pa.swarmsimulator.model.area.RectangleArea;
import it.unicam.cs.pa.swarmsimulator.model.area.SignalingArea;
import it.unicam.cs.pa.swarmsimulator.model.robot.Robot;
import it.unicam.cs.pa.swarmsimulator.model.robot.RobotFactory;
import it.unicam.cs.pa.swarmsimulator.model.robot.SignalingCondition;
import it.unicam.cs.pa.swarmsimulator.model.robotstate.StandardState;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class StandardBuilder implements EnvironmentBuilder<StandardState, PlainLocation> {
    private final FollowMeParser parser;
    private final RobotFactory<StandardState> robotFactory;

    public StandardBuilder(FollowMeParser parser, RobotFactory<StandardState> robotFactory) {
        this.parser = Objects.requireNonNull(parser);
        this.robotFactory = Objects.requireNonNull(robotFactory);
    }

    @Override
    public Environment<StandardState, PlainLocation> build(File file, int n) throws FollowMeParserException, IOException {
        Map<Robot<StandardState>, PlainLocation> robotConfig = new HashMap<>();
        List<SignalingArea<PlainLocation>> areas = new ArrayList<>();

        robotFactory.createRobots(n).forEach(r -> robotConfig.put(r, new PlainLocation()));
        parser.parseEnvironment(file).forEach(s -> areas.add(shapeDataToArea(s)));

        return new PlainEnvironment(robotConfig, areas);
    }

    private SignalingArea<PlainLocation> shapeDataToArea(ShapeData s) {
        if (s.shape().equalsIgnoreCase("circle"))
            return new CircleArea(new PlainLocation(s.args()[2], s.args()[3]), s.label(), s.args()[4]);
        return new RectangleArea(new PlainLocation(s.args()[2], s.args()[3]), s.label(), s.args()[4], s.args()[5]);
    }
}

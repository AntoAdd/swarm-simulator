package it.unicam.cs.pa.swarmsimulator.model.commands;

import it.unicam.cs.pa.swarmsimulator.model.environment.PlainLocation;
import it.unicam.cs.pa.swarmsimulator.model.environment.Position;
import it.unicam.cs.pa.swarmsimulator.model.robot.Speed;

public record MoveRandom(Position<PlainLocation> first, Position<PlainLocation> second,
                         Speed speed) implements RobotCommand {
    @Override
    public RobotCommand getCopy() {
        return new MoveRandom(
            new PlainLocation(first.getPositionCoordinates().x(), first.getPositionCoordinates().y()),
            new PlainLocation(second.getPositionCoordinates().x(), second.getPositionCoordinates().y()),
            new Speed(speed.getValue())
        );
    }

    @Override
    public void reset() {

    }
}

package it.unicam.cs.pa.swarmsimulator.model.commands;

import it.unicam.cs.pa.swarmsimulator.model.PlainLocation;
import it.unicam.cs.pa.swarmsimulator.model.Position;
import it.unicam.cs.pa.swarmsimulator.model.robot.Speed;

public record Move(Position<PlainLocation> target,
                   Speed speed) implements RobotCommand {
    @Override
    public RobotCommand getCopy() {
        return new Move(new PlainLocation(target.getPositionCoordinates().x(), target.getPositionCoordinates().y()),
            new Speed(speed().getValue()));
    }

    @Override
    public void reset() {

    }
}

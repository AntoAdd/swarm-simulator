package it.unicam.cs.pa.swarmsimulator.model.commands;

import it.unicam.cs.pa.swarmsimulator.model.robot.SignalingCondition;
import it.unicam.cs.pa.swarmsimulator.model.robot.Speed;

public record Follow(SignalingCondition condition, double distance, Speed speed) implements RobotCommand {

    @Override
    public RobotCommand getCopy() {
        return new Follow(new SignalingCondition(condition().getValue()), distance, new Speed(speed.getValue()));
    }

    @Override
    public void reset() {

    }
}

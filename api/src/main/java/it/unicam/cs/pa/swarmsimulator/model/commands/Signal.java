package it.unicam.cs.pa.swarmsimulator.model.commands;

import it.unicam.cs.pa.swarmsimulator.model.robot.SignalingCondition;

public record Signal(SignalingCondition condition) implements RobotCommand {
}

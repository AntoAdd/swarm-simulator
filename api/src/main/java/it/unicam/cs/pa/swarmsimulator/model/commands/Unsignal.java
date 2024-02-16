package it.unicam.cs.pa.swarmsimulator.model.commands;

import it.unicam.cs.pa.swarmsimulator.model.robot.SignalingCondition;

public record Unsignal(SignalingCondition condition) implements RobotCommand {
}

package it.unicam.cs.pa.swarmsimulator.model.commands;

import it.unicam.cs.pa.swarmsimulator.model.robot.SignalingCondition;
import it.unicam.cs.pa.swarmsimulator.model.robot.Speed;

public record Follow(SignalingCondition condition, double distance, Speed speed) implements RobotCommand {

}

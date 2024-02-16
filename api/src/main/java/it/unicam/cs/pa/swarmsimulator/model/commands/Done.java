package it.unicam.cs.pa.swarmsimulator.model.commands;

import java.util.Objects;

public record Done(RobotCommand containerCommand) implements RobotCommand {
    public Done(RobotCommand containerCommand) {
        this.containerCommand = Objects.requireNonNull(containerCommand);
    }
}

package it.unicam.cs.pa.swarmsimulator.model.commands;

public final class Stop implements RobotCommand{
    @Override
    public RobotCommand getCopy() {
        return new Stop();
    }
}

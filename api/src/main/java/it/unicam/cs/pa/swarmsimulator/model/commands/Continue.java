package it.unicam.cs.pa.swarmsimulator.model.commands;

public final class Continue implements RobotCommand {
    private int seconds;

    public Continue(int seconds) {
        this.seconds = seconds;
    }

    public int getSeconds() {
        return seconds;
    }

    public void decreaseSecs(){
        this.seconds--;
    }

    @Override
    public boolean hasFinishedExecution() {
        return seconds == 0;
    }

    @Override
    public RobotCommand getCopy() {
        return new Continue(seconds);
    }
}

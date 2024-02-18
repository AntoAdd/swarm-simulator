package it.unicam.cs.pa.swarmsimulator.model.commands;

public final class Continue implements RobotCommand {
    private final int seconds;
    private int remainingSeconds;

    public Continue(int seconds) {
        this.seconds = seconds;
        this.remainingSeconds = seconds;
    }

    public int getRemainingSeconds() {
        return remainingSeconds;
    }

    public void decreaseSecs(){
        this.remainingSeconds--;
    }

    @Override
    public boolean hasFinishedExecution() {
        return remainingSeconds == 0;
    }

    @Override
    public RobotCommand getCopy() {
        return new Continue(remainingSeconds);
    }

    @Override
    public void reset() {
        this.remainingSeconds = seconds;
    }
}

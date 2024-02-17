package it.unicam.cs.pa.swarmsimulator.model.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class Repeat implements RobotCommand {
    private final int reps;
    private int remainingReps;
    private int nextSubcommandIndex;
    private final List<RobotCommand> subcommands;

    public Repeat(int reps, List<RobotCommand> subcommands) {
        this.reps = reps;
        this.subcommands = Collections.unmodifiableList(subcommands);
        this.remainingReps = reps;
        this.nextSubcommandIndex = 0;
    }

    public Repeat(int reps) {
        this.reps = reps;
        this.subcommands = new ArrayList<>();
        this.remainingReps = reps;
        this.nextSubcommandIndex = 0;
    }

    public void addCommand(RobotCommand command){
        subcommands.add(Objects.requireNonNull(command));
    }

    public int getReps() {
        return reps;
    }

    public int getRemainingReps() {
        return remainingReps;
    }

    public int getNextSubcommandIndex() {
        return nextSubcommandIndex;
    }

    public void setNextSubcommandIndex(int nextSubcommandIndex) {
        this.nextSubcommandIndex = nextSubcommandIndex;
    }

    public void decreaseRemainingReps(){
        remainingReps--;
    }

    public RobotCommand getNextSubcommand(){
        return this.subcommands.get(nextSubcommandIndex);
    }

    @Override
    public List<RobotCommand> getSubcommandsList() {
        return subcommands;
    }

    @Override
    public boolean hasFinishedExecution() {
        return remainingReps == 0;
    }

    @Override
    public RobotCommand getCopy() {
        List<RobotCommand> subcommandsCopy = new ArrayList<>();
        for (RobotCommand c :
            subcommands) {
            subcommandsCopy.add(c.getCopy());
        }
        return new Repeat(reps, subcommandsCopy);
    }
}

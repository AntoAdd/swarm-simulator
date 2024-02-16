package it.unicam.cs.pa.swarmsimulator.model.commands;

import java.util.Collections;
import java.util.List;

public final class Forever implements RobotCommand {
    private final List<RobotCommand> subcommands;

    private int nextSubcommandIndex;

    public Forever(List<RobotCommand> subcommands) {
        this.subcommands = Collections.unmodifiableList(subcommands);
        this.nextSubcommandIndex = 0;
    }

    public int getNextSubcommandIndex() {
        return nextSubcommandIndex;
    }

    public void setNextSubcommandIndex(int nextSubcommandIndex) {
        this.nextSubcommandIndex = nextSubcommandIndex;
    }

    public RobotCommand getNextSubcommand(){
        return subcommands.get(nextSubcommandIndex);
    }

    @Override
    public List<RobotCommand> getSubcommandsList() {
        return subcommands;
    }

    @Override
    public boolean hasFinishedExecution() {
        return false;
    }
}

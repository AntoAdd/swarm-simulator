package it.unicam.cs.pa.swarmsimulator.model.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class Forever implements RobotCommand {
    private final List<RobotCommand> subcommands;
    private int nextSubcommandIndex;

    public Forever(List<RobotCommand> subcommands) {
        this.subcommands = Collections.unmodifiableList(subcommands);
        this.nextSubcommandIndex = 0;
    }

    public Forever() {
        subcommands = new ArrayList<>();
        nextSubcommandIndex = 0;
    }

    public void addSubcommand(RobotCommand command){
        subcommands.add(Objects.requireNonNull(command));
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

    @Override
    public RobotCommand getCopy() {
        List<RobotCommand> subcommandsCopy = new ArrayList<>();
        for (RobotCommand c :
            subcommands) {
            subcommandsCopy.add(c.getCopy());
        }
        return new Forever(subcommandsCopy);
    }

    @Override
    public void reset() {

    }
}

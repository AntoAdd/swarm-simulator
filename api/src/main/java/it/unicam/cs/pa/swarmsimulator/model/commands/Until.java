package it.unicam.cs.pa.swarmsimulator.model.commands;

import it.unicam.cs.pa.swarmsimulator.model.robot.SignalingCondition;

import java.util.Collections;
import java.util.List;

public final class Until implements RobotCommand {
    private final SignalingCondition condition;
    private int nextSubcommandIndex;

    private boolean hasMeatCondition;
    private final List<RobotCommand> subcommands;

    public Until(SignalingCondition condition, List<RobotCommand> subcommands) {
        this.condition = condition;
        this.subcommands = Collections.unmodifiableList(subcommands);
        this.nextSubcommandIndex = 0;
        this.hasMeatCondition = false;
    }

    public SignalingCondition getCondition() {
        return condition;
    }

    public int getNextSubcommandIndex() {
        return nextSubcommandIndex;
    }

    public void setNextSubcommandIndex(int nextSubcommandIndex) {
        this.nextSubcommandIndex = nextSubcommandIndex;
    }

    public void setHasMeatCondition(boolean hasMeatCondition) {
        this.hasMeatCondition = hasMeatCondition;
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
        return hasMeatCondition;
    }
}

package it.unicam.cs.pa.swarmsimulator.model.commands;

import it.unicam.cs.pa.swarmsimulator.model.robot.SignalingCondition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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

    public Until(SignalingCondition condition) {
        this.condition = condition;
        this.subcommands = new ArrayList<>();
        this.nextSubcommandIndex = 0;
        this.hasMeatCondition = false;
    }

    public void addSubcommand(RobotCommand command){
        subcommands.add(Objects.requireNonNull(command));
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

    @Override
    public RobotCommand getCopy() {
        List<RobotCommand> subcommandsCopy = new ArrayList<>();
        for (RobotCommand c :
            subcommands) {
            subcommandsCopy.add(c.getCopy());
        }
        return new Until(new SignalingCondition(condition.getValue()), subcommandsCopy);
    }

    @Override
    public void reset() {

    }
}

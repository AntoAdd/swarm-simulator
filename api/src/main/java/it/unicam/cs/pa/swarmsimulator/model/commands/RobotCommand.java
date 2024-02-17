package it.unicam.cs.pa.swarmsimulator.model.commands;

import java.util.Collections;
import java.util.List;

/**
 * This interface is used to define a robot command executable by the programExecutor.
 */
public sealed interface RobotCommand permits Continue, Done, Follow, Forever, Move, MoveRandom, Repeat, Signal, Stop, Unsignal, Until {
    /**
     * Returns the list of subcommands in a selection/iteration command.
     * In case of a simple command or a selection/iteration commands with no subcommands, an
     * empty list is returned.
     *
     * @return the list of subcommands in a selection/iteration command.
     */
    default List<RobotCommand> getSubcommandsList(){
        return Collections.emptyList();
    }

    default boolean hasFinishedExecution(){
        return true;
    }

    RobotCommand getCopy();
}

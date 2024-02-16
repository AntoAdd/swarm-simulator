package it.unicam.cs.pa.swarmsimulator.model;

import it.unicam.cs.pa.swarmsimulator.model.commands.RobotCommand;
import it.unicam.cs.pa.swarmsimulator.model.robot.Robot;
import it.unicam.cs.pa.swarmsimulator.model.robotstate.RobotState;
import it.unicam.cs.pa.swarmsimulator.model.util.Pair;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * A program executor is responsible for the execution of robot commands: given a program to execute,
 * the executor keeps track of the execution progression for each robot and execute the appropriate
 * command that a given robot need to execute next.
 *
 * @param <S> the type of robot state.
 * @param <C> the type of positions used in the environment.
 */
public interface ProgramExecutor<S extends RobotState, C extends Position<C>> {

    /**
     * Returns a map representing the current program execution state for the swarm.
     *
     * Each robot is mapped to a list of pair: the first element of the pair represents the index of
     * the next command to execute for that robot, the second the value corresponding to repetition
     * for a repeat command or to seconds in case of a continue command (an <code>Optional.empty()</code>
     * in case of all other types of commands.
     *
     * When a selection/iteration command is encountered during the execution, a new pair is added
     * to the list, representing the nested progression in the command subcommands. Instead, when
     * the execution of a selection/iteration command terminates, the last pair is removed, and the
     * execution goes up one level of nesting.
     *
     * @return a map representing the current program execution state for the swarm.
     */
    Map<Robot<S>, List<Pair<Integer, Optional<Integer>>>> getCurrentExecutionState();


    /**
     * Executes the next command for the given robot (if it has any left commands to execute) and
     * returns a pair with the new state and the new position of the robot after the command execution.
     *
     * If the given robot has no more commands to execute, an <code>Optional.empty()</code> is returned.
     *
     * @param robot the given robot.
     * @return a pair with the new state and the new position of the robot after the command execution.
     */
    Optional<Pair<S, C>> executeNextCommandFor(Robot<S> robot);
}

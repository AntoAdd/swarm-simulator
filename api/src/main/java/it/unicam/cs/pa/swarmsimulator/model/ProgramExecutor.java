package it.unicam.cs.pa.swarmsimulator.model;

/**
 * A Program Executor executes commands of a robot program. Each robot uses a program executor to run its commands.
 */
public interface ProgramExecutor<S extends RobotState> {
    /**
     * Returns true if there are no more commands to execute, false otherwise.
     *
     * @return true if there are no more commands to execute, false otherwise.
     */
    boolean hasFinishedExecution();

    /**
     * Returns the index of the next command to execute.
     *
     * @return the index of the next command to execute.
     */
    int getNextCommandIndex();

    /**
     * Executes the program for the given robot.
     *
     * @param r the given robot.
     */
    void executeProgramFor(Robot<S> r);

    /**
     * Executes the given command and returns the navigation state
     * deriving from that execution.
     *
     * @param c the command to execute.
     * @return the navigation state deriving from the execution of the given command.
     */
    S executeCommand(RobotCommand<S> c);
}

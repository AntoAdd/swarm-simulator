package it.unicam.cs.pa.swarmsimulator.model;

/**
 * A robot command produces a navigation state for the robot that executes it.
 * A command can be executed on different type of inputs or on no input at all.
 *
 * @param <S> the type of navigation state obtained from the execution of this command.
 */
public interface RobotCommand<S extends RobotState> {
    /**
     * Returns the navigation state obtained from the execution of this command on its inputs.
     *
     * @param inputs the given inputs on which the command has to execute.
     * @return the navigation state obtained from the execution of this command on its inputs.
     */
    S execute(CommandInput ... inputs);
}

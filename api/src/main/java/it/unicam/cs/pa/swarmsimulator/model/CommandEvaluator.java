package it.unicam.cs.pa.swarmsimulator.model;

import it.unicam.cs.pa.swarmsimulator.model.commands.RobotCommand;
import it.unicam.cs.pa.swarmsimulator.model.robot.Robot;
import it.unicam.cs.pa.swarmsimulator.model.robotstate.RobotState;
import it.unicam.cs.pa.swarmsimulator.model.util.Pair;

public interface CommandEvaluator<S extends RobotState, C extends Position<C>> {
    /**
     * Returns the environment in which the evaluator is performing commands evaluations.
     *
     * @return the environment in which the evaluator is performing commands evaluations.
     */
    Environment<S, C> getEnvironment();

    /**
     * Set the evaluation environment to the given environment.
     *
     * @param environment the given environment.
     */
    void setEnvironment(Environment<S, C> environment);

    /**
     * Evaluates the given command for the given robot.
     *
     * Returns a pair in which the first element represents the state of the given robot after the command
     * execution and the second the new position of the robot in the environment. If the position of the
     * robot hasn't changed, the old position is returned.
     *
     * @param robot the robot for which the command is evaluated.
     * @param command the command to evaluate.
     * @return a pair in which the first element represents the state of the given robot after the command
     *         execution and the second the new position of the robot in the environment.
     */
    Pair<S, C> evaluate(Robot<S> robot, RobotCommand command);
}

package it.unicam.cs.pa.swarmsimulator.io;

import it.unicam.cs.pa.swarmsimulator.model.Environment;
import it.unicam.cs.pa.swarmsimulator.model.Position;
import it.unicam.cs.pa.swarmsimulator.model.robotstate.RobotState;

import java.io.File;
import java.io.IOException;

/**
 * This interface is used to build the simulation environment from a given file.
 *
 * The builder creates a robot configuration where each robot is placed in a random position in the
 * environment.
 *
 * @param <S> the type of robot state.
 * @param <C> the type of positions.
 */
public interface EnvironmentBuilder<S extends RobotState, C extends Position<C>> {
    /**
     * Returns the new built environment.
     *
     * @param file the file used to parse shapes.
     * @param n the number of robots to create.
     * @return the new built environment.
     */
    Environment<S, C> build(File file, int n) throws FollowMeParserException, IOException;
}

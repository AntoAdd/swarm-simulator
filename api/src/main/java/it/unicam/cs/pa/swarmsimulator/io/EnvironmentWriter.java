package it.unicam.cs.pa.swarmsimulator.io;

import it.unicam.cs.pa.swarmsimulator.model.Environment;
import it.unicam.cs.pa.swarmsimulator.model.Position;
import it.unicam.cs.pa.swarmsimulator.model.robotstate.RobotState;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * An environment writer is responsible to write an environment configuration to a file.
 */
public interface EnvironmentWriter<S extends RobotState, C extends Position<C>> {
    /**
     * Returns a string representing the given environment.
     *
     * @param environment the given environment.
     * @return a string representing the given environment.
     */
    String stringOf(Environment<S, C> environment);

    default void writeTo(File file, Environment<S, C> environment) throws IOException {
        writeTo(file.toPath(), environment);
    }

    default void writeTo(Path path, Environment<S, C> environment) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path.toString()));
        if (br.readLine() == null) {
            Files.write(path, stringOf(environment).getBytes());
        } else {
            Files.write(path, stringOf(environment).getBytes(), StandardOpenOption.APPEND);
        }
    }

}

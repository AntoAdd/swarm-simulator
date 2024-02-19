package it.unicam.cs.pa.swarmsimulator.io;

import it.unicam.cs.pa.swarmsimulator.model.environment.Environment;
import it.unicam.cs.pa.swarmsimulator.model.environment.PlainLocation;
import it.unicam.cs.pa.swarmsimulator.model.robotstate.StandardState;

public class StandardWriter implements EnvironmentWriter<StandardState, PlainLocation> {
    @Override
    public String stringOf(Environment<StandardState, PlainLocation> environment) {
        return environment.toString() + "\n";
    }
}

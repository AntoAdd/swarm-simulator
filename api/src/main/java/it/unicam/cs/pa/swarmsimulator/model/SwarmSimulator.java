package it.unicam.cs.pa.swarmsimulator.model;

import it.unicam.cs.pa.swarmsimulator.io.*;
import it.unicam.cs.pa.swarmsimulator.model.commands.RobotCommand;
import it.unicam.cs.pa.swarmsimulator.model.environment.Environment;
import it.unicam.cs.pa.swarmsimulator.model.environment.PlainLocation;
import it.unicam.cs.pa.swarmsimulator.model.execution.ProgramExecutor;
import it.unicam.cs.pa.swarmsimulator.model.execution.StandardProgramExecutor;
import it.unicam.cs.pa.swarmsimulator.model.robotstate.StandardState;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class SwarmSimulator implements Simulator {
    private final EnvironmentBuilder<StandardState, PlainLocation> builder;
    private final EnvironmentWriter<StandardState, PlainLocation> writer;
    private final ProgramExecutor<StandardState, PlainLocation> executor;
    private Environment<StandardState, PlainLocation> currentEnvironment;
    private final File results;

    public SwarmSimulator(
        EnvironmentBuilder<StandardState, PlainLocation> builder,
        EnvironmentWriter<StandardState, PlainLocation> writer,
        File envFile,
        File programFile,
        File results,
        int numOfRobots
    ) throws FollowMeParserException, IOException {
        this.builder = Objects.requireNonNull(builder);
        this.writer = Objects.requireNonNull(writer);
        this.results = results;
        this.currentEnvironment = this.builder.build(envFile, numOfRobots);
        this.executor = initializeExecutor(programFile, new FollowMeParser(new ProgramParserHandler()));
    }

    private ProgramExecutor<StandardState, PlainLocation> initializeExecutor(File programFile, FollowMeParser followMeParser) throws FollowMeParserException, IOException {
        followMeParser.parseRobotProgram(programFile);
        List<RobotCommand> parsedProgram;
        if (followMeParser.getHandler().getParsedProgram().isPresent())
            parsedProgram = followMeParser.getHandler().getParsedProgram().get();
        else
            throw new IllegalStateException("error during robot parsing.");
        return new StandardProgramExecutor(parsedProgram, this.currentEnvironment);
    }

    @Override
    public void simulate(double dt, double time) throws IOException {
        writer.writeTo(results, currentEnvironment);
        double timeProgress = 0.0;
        while ((timeProgress + dt) <= time){
            executor.stepForward();
            currentEnvironment = executor.getCurrentConfiguration();
            writer.writeTo(results, currentEnvironment);
            timeProgress+= dt;
        }
    }
}

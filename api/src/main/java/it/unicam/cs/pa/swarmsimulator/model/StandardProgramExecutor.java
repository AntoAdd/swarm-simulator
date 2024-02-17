package it.unicam.cs.pa.swarmsimulator.model;

import it.unicam.cs.pa.swarmsimulator.model.commands.RobotCommand;
import it.unicam.cs.pa.swarmsimulator.model.robot.Robot;
import it.unicam.cs.pa.swarmsimulator.model.robotstate.StandardState;
import it.unicam.cs.pa.swarmsimulator.model.util.Pair;

import java.util.*;

public class StandardProgramExecutor implements ProgramExecutor<StandardState, PlainLocation> {
    private final List<RobotCommand> program;
    private Environment<StandardState, PlainLocation> currentEnvironment;
    private final CommandEvaluator<StandardState, PlainLocation> evaluator;
    private final Map<Robot<StandardState>, Pair<Integer, List<RobotCommand>>> executionProgress;

    public StandardProgramExecutor(List<RobotCommand> program, Environment<StandardState, PlainLocation> currentEnvironment) {
        this.program = Objects.requireNonNull(program);
        this.currentEnvironment = Objects.requireNonNull(currentEnvironment);
        this.evaluator = new StandardEvaluator(currentEnvironment);
        this.executionProgress = new HashMap<>();
        initializeExecutionProgressMap();
    }

    private void initializeExecutionProgressMap() {
        currentEnvironment.getRobots().forEach(r -> executionProgress.put(r, new Pair<>(0, getProgramCopy())));
    }

    private List<RobotCommand> getProgramCopy() {
        List<RobotCommand> copy = new ArrayList<>();
        for (RobotCommand c :
            program) {
            copy.add(c.getCopy());
        }
        return copy;
    }

    @Override
    public void stepForward() {
        Map<Robot<StandardState>, PlainLocation> resultMap = new HashMap<>();

        for (Robot<StandardState> r : executionProgress.keySet()) {
            int nextCommandIndex = executionProgress.get(r).getFirst();
            RobotCommand nextCommand = executionProgress.get(r).getSecond().get(nextCommandIndex);
            Pair<StandardState, PlainLocation> evaluationResult = evaluator.evaluate(r, nextCommand);

            if (nextCommand.hasFinishedExecution() && (executionProgress.get(r).getFirst() + 1) < program.size()){
                executionProgress.get(r).setFirst(nextCommandIndex + 1);
            }

            r.updateNavigationState(evaluationResult.getFirst());
            resultMap.put(r, evaluationResult.getSecond());
        }

        Environment<StandardState, PlainLocation> resultEnvironment = currentEnvironment.evolve(resultMap);
        currentEnvironment = resultEnvironment;
        evaluator.setEnvironment(resultEnvironment);
    }

    @Override
    public Environment<StandardState, PlainLocation> getCurrentConfiguration() {
        return currentEnvironment;
    }
}

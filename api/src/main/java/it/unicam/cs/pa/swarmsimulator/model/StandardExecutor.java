package it.unicam.cs.pa.swarmsimulator.model;

import it.unicam.cs.pa.swarmsimulator.model.commands.*;
import it.unicam.cs.pa.swarmsimulator.model.robot.Direction;
import it.unicam.cs.pa.swarmsimulator.model.robot.Robot;
import it.unicam.cs.pa.swarmsimulator.model.robotstate.StandardState;
import it.unicam.cs.pa.swarmsimulator.model.util.Pair;

import java.util.*;

public class StandardExecutor implements ProgramExecutor<StandardState, PlainLocation> {
    private final Environment<StandardState, PlainLocation> environment;
    private final Map<Robot<StandardState>, List<Pair<Integer, Optional<Integer>>>> executionState = new HashMap<>();
    private final List<RobotCommand> robotProgram;

    public StandardExecutor(Environment<StandardState, PlainLocation> environment, List<RobotCommand> robotProgram) {
        this.environment = Objects.requireNonNull(environment);
        this.robotProgram = Collections.unmodifiableList(robotProgram);
        initializeExecution(this.environment.getRobots());
    }

    private void initializeExecution(Set<Robot<StandardState>> robots) {
        for (Robot<StandardState> r :
            robots) {
            List<Pair<Integer, Optional<Integer>>> execStateList = new ArrayList<>();
            execStateList.add(new Pair<>(0, Optional.empty()));
            executionState.put(r, execStateList);
        }
    }

    @Override
    public Map<Robot<StandardState>, List<Pair<Integer, Optional<Integer>>>> getCurrentExecutionState() {
        return executionState;
    }

    @Override
    public Optional<Pair<StandardState, PlainLocation>> executeNextCommandFor(Robot<StandardState> robot) {
        Optional<RobotCommand> next = getNextCommandFor(robot);
        if (next.isPresent()){
            RobotCommand command = next.get();
            return switch (command){
                case Move move -> executeMove(robot, move);
                case Follow follow -> null;
                case Signal signal -> executeSignal(robot, signal);
                case Forever forever -> null;
                case MoveRandom moveRandom -> null;
                case Continue aContinue -> null;
                case Unsignal unsignal -> null;
                case Done done -> null;
                case Stop stop -> null;
                case Until until -> null;
                case Repeat repeat -> null;
            };
        }
        return Optional.empty();
    }

    private Optional<Pair<StandardState, PlainLocation>> executeSignal(Robot<StandardState> robot, Signal signal) {
        PlainLocation position = environment.getRobotPosition(robot);
        StandardState nextState = new StandardState(
            robot.getNavigationState().getSpeed(),
            robot.getNavigationState().getDirection(),
            signal.condition()
        );

        advanceExecution(robot);

        return Optional.of(new Pair<>(nextState, position));
    }

    private void advanceExecution(Robot<StandardState> robot) {
        Pair<Integer, Optional<Integer>> commandPair =
            executionState.get(robot).get(executionState.get(robot).size() - 1);
        commandPair.setFirst(commandPair.getFirst() + 1);
    }

    private Optional<Pair<StandardState, PlainLocation>> executeMove(Robot<StandardState> robot, Move move) {
        PlainLocation position = environment.getRobotPosition(robot);
        Direction direction = position.directionTo(move.target().getPositionCoordinates());
        StandardState nextState = new StandardState(move.speed(), direction, robot.getNavigationState().getCondition());

        advanceExecution(robot);

        return Optional.of(new Pair<>(nextState, position));
    }


    private Optional<RobotCommand> getNextCommandFor(Robot<StandardState> robot) {
        List<Pair<Integer, Optional<Integer>>> execState = executionState.get(robot);

        if (execState.size() == 1 && execState.get(0).getFirst() < robotProgram.size())
            return Optional.of(robotProgram.get(execState.get(0).getFirst()));
        else if (execState.size() > 1) {
            Iterator<Pair<Integer, Optional<Integer>>> iterator = execState.iterator();
            RobotCommand command = robotProgram.get(iterator.next().getFirst());
            while (iterator.hasNext()){
                command = command.getSubcommandsList().get(iterator.next().getFirst());
            }
            return Optional.of(command);
        }
        return Optional.empty();
    }
}

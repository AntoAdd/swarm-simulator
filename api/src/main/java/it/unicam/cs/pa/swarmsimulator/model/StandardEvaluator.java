package it.unicam.cs.pa.swarmsimulator.model;

import it.unicam.cs.pa.swarmsimulator.model.area.SignalingArea;
import it.unicam.cs.pa.swarmsimulator.model.commands.*;
import it.unicam.cs.pa.swarmsimulator.model.robot.Direction;
import it.unicam.cs.pa.swarmsimulator.model.robot.Robot;
import it.unicam.cs.pa.swarmsimulator.model.robot.SignalingCondition;
import it.unicam.cs.pa.swarmsimulator.model.robot.Speed;
import it.unicam.cs.pa.swarmsimulator.model.robotstate.StandardState;
import it.unicam.cs.pa.swarmsimulator.model.util.Pair;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class StandardEvaluator implements CommandEvaluator<StandardState, PlainLocation> {
    private Environment<StandardState, PlainLocation> evaluationEnvironment;

    public StandardEvaluator(Environment<StandardState, PlainLocation> evaluationEnvironment) {
        this.evaluationEnvironment = Objects.requireNonNull(evaluationEnvironment);
    }

    @Override
    public Environment<StandardState, PlainLocation> getEnvironment() {
        return evaluationEnvironment;
    }

    @Override
    public void setEnvironment(Environment<StandardState, PlainLocation> environment) {
        this.evaluationEnvironment = Objects.requireNonNull(environment);
    }

    @Override
    public Pair<StandardState, PlainLocation> evaluate(Robot<StandardState> robot, RobotCommand command) {
        return switch (command){
            case Move move -> evaluateMove(robot, move);
            case MoveRandom moveRandom -> evaluateMoveRandom(robot, moveRandom);
            case Repeat repeat -> evaluateRepeat(robot, repeat);
            case Unsignal unsignal -> evaluateUnsignal(robot, unsignal);
            case Signal signal -> evaluateSignal(robot, signal);
            case Follow follow -> evaluateFollow(robot, follow);
            case Done done -> evaluateDone(robot, done);
            case Forever forever -> evaluateForever(robot, forever);
            case Stop ignored -> evaluateStop(robot);
            case Continue continueCommand -> evaluateContinue(robot, continueCommand);
            case Until until -> evaluateUntil(robot, until);
        };
    }

    private Pair<StandardState, PlainLocation> evaluateDone(Robot<StandardState> robot, Done done) {
        switch (done.containerCommand()) {
            case Repeat repeat -> {
                repeat.decreaseRemainingReps();
                repeat.setNextSubcommandIndex(0);
            }
            case Forever forever -> forever.setNextSubcommandIndex(0);
            case Until until -> until.setNextSubcommandIndex(0);
            default -> throw new IllegalStateException("Unexpected value: " + done.containerCommand());
        }
        return new Pair<>(
            robot.getNavigationState(), evaluationEnvironment.getRobotPosition(robot)
        );
    }

    private Pair<StandardState, PlainLocation> evaluateUntil(Robot<StandardState> robot, Until until) {
        if (checkIfPerceived(robot, until.getCondition())){
            until.setHasMeatCondition(true);
            return new Pair<>(robot.getNavigationState(), evaluationEnvironment.getRobotPosition(robot));
        } else {
            RobotCommand command = until.getNextSubcommand();
            until.setNextSubcommandIndex(until.getNextSubcommandIndex() + 1);
            return evaluate(robot, command);
        }
    }

    private boolean checkIfPerceived(Robot<StandardState> robot, SignalingCondition condition) {
        List<SignalingArea<PlainLocation>> areasForRobot = evaluationEnvironment.getAreasOccupiedBy(robot);
        if (!areasForRobot.isEmpty()){
            for (SignalingArea<PlainLocation> a :
                areasForRobot) {
                if (a.getCondition().equals(condition.getValue()))
                    return true;
            }
        }
        return false;
    }

    private Pair<StandardState, PlainLocation> evaluateContinue(Robot<StandardState> robot, Continue continueCommand) {
        PlainLocation target = calculateTargetPosition(robot);
        continueCommand.decreaseSecs();
        return new Pair<>(robot.getNavigationState(), target);
    }

    private PlainLocation calculateTargetPosition(Robot<StandardState> robot) {
        StandardState state = robot.getNavigationState();
        PlainLocation source = evaluationEnvironment.getRobotPosition(robot);
        double radiansAngle = Math.toRadians(state.getDirection().getValue());
        double xTarget = source.x() + state.getSpeed().getValue() * Math.cos(radiansAngle);
        double yTarget = source.y() + state.getSpeed().getValue() * Math.sin(radiansAngle);
        return new PlainLocation(xTarget, yTarget);
    }

    private Pair<StandardState, PlainLocation> evaluateStop(Robot<StandardState> robot) {
        StandardState state = robot.getNavigationState();
        return new Pair<>(
            new StandardState(new Speed(0.0), state.getDirection(), state.getCondition()),
            evaluationEnvironment.getRobotPosition(robot)
        );
    }

    private Pair<StandardState, PlainLocation> evaluateForever(Robot<StandardState> robot, Forever forever) {
        RobotCommand command = forever.getNextSubcommand();
        forever.setNextSubcommandIndex(forever.getNextSubcommandIndex() + 1);
        return evaluate(robot, command);
    }

    private Pair<StandardState, PlainLocation> evaluateFollow(Robot<StandardState> robot, Follow follow) {
        List<Robot<StandardState>> signalingRobots = evaluationEnvironment.getSignalingRobotsInRange(
            robot, follow.condition().getValue(), follow.distance()
        );
        Direction direction = evaluationEnvironment.getRobotPosition(robot).directionTo(calculateRandomTarget(
                new PlainLocation(-follow.distance(), -follow.distance()),
                new PlainLocation(follow.distance(), follow.distance()))
            );
        if (!signalingRobots.isEmpty()){
            PlainLocation meanPosition = calculateMeanPosition(evaluationEnvironment.getPositionsOf(signalingRobots));
            direction = evaluationEnvironment.getRobotPosition(robot).directionTo(meanPosition);
        }
        return new Pair<>(
            new StandardState(follow.speed(), direction, robot.getNavigationState().getCondition()),
            evaluationEnvironment.getRobotPosition(robot)
        );
    }

    private PlainLocation calculateMeanPosition(List<PlainLocation> positions) {
        double xSum = 0;
        double ySum = 0;
        for (PlainLocation p:
             positions) {
            xSum += p.x();
            ySum += p.y();
        }
        return new PlainLocation(xSum / positions.size(), ySum / positions.size());
    }

    private Pair<StandardState, PlainLocation> evaluateSignal(Robot<StandardState> robot, Signal signal) {
        StandardState state = robot.getNavigationState();
        return new Pair<>(
            new StandardState(state.getSpeed(), state.getDirection(), signal.condition()),
            evaluationEnvironment.getRobotPosition(robot)
        );
    }

    private Pair<StandardState, PlainLocation> evaluateUnsignal(Robot<StandardState> robot, Unsignal unsignal) {
        StandardState state = robot.getNavigationState();
        if (state.getCondition().equals(unsignal.condition())){
            return new Pair<>(
                new StandardState(state.getSpeed(), state.getDirection(), new SignalingCondition("")),
                evaluationEnvironment.getRobotPosition(robot)
            );
        }
        return new Pair<>(state, evaluationEnvironment.getRobotPosition(robot));
    }

    private Pair<StandardState, PlainLocation> evaluateRepeat(Robot<StandardState> robot, Repeat repeat) {
        if (!repeat.hasFinishedExecution()){
            RobotCommand nextSubcommand = repeat.getNextSubcommand();
            repeat.setNextSubcommandIndex(repeat.getNextSubcommandIndex() + 1);
            return evaluate(robot, nextSubcommand);
        }
        return new Pair<>(robot.getNavigationState(), evaluationEnvironment.getRobotPosition(robot));
    }

    private Pair<StandardState, PlainLocation> evaluateMoveRandom(Robot<StandardState> robot, MoveRandom moveRandom) {
        PlainLocation position = evaluationEnvironment.getRobotPosition(robot);
        PlainLocation target = calculateRandomTarget(moveRandom.first(), moveRandom.second());
        Direction direction = position.directionTo(target);
        return new Pair<>(
            new StandardState(moveRandom.speed(), direction, robot.getNavigationState().getCondition()),
            position
        );
    }

    private PlainLocation calculateRandomTarget(Position<PlainLocation> first, Position<PlainLocation> second) {
        Random r = new Random();
        double randomX = first.getPositionCoordinates().x() +
            (second.getPositionCoordinates().x() - first.getPositionCoordinates().x()) * r.nextDouble();
        double randomY = first.getPositionCoordinates().y() +
            (second.getPositionCoordinates().y() - first.getPositionCoordinates().y()) * r.nextDouble();
        return new PlainLocation(randomX, randomY);
    }

    private Pair<StandardState, PlainLocation> evaluateMove(Robot<StandardState> robot, Move move) {
        PlainLocation position = evaluationEnvironment.getRobotPosition(robot);
        Direction direction = position.directionTo(move.target().getPositionCoordinates());
        return new Pair<>(
            new StandardState(move.speed(), direction, robot.getNavigationState().getCondition()),
            position
        );
    }
}

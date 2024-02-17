package it.unicam.cs.pa.swarmsimulator.model;

import it.unicam.cs.pa.swarmsimulator.model.area.SignalingArea;
import it.unicam.cs.pa.swarmsimulator.model.robot.Robot;
import it.unicam.cs.pa.swarmsimulator.model.robotstate.StandardState;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A plain environment describes the plain infinite surface where the swarm moves.
 */
public class PlainEnvironment implements Environment<StandardState, PlainLocation> {
    private final Map<Robot<StandardState>, PlainLocation> robotConfiguration;
    private final List<SignalingArea<PlainLocation>> signalingAreas;

    public PlainEnvironment(
        Map<Robot<StandardState>, PlainLocation> robotConfiguration,
        List<SignalingArea<PlainLocation>> signalingAreas
    ) {
        this.robotConfiguration = Objects.requireNonNull(robotConfiguration);
        this.signalingAreas = Objects.requireNonNull(signalingAreas);
    }

    @Override
    public Robot<StandardState> getRobot(long id) {
        return robotConfiguration.keySet().stream()
            .filter(r -> r.getId() == id)
            .findFirst()
            .orElseThrow();
    }

    @Override
    public
    Set<Robot<StandardState>> getRobots() {
        return robotConfiguration.keySet();
    }

    @Override
    public PlainLocation getRobotPosition(Robot<StandardState> robot) {
        return robotConfiguration.get(robot);
    }

    @Override
    public List<PlainLocation> getRobotPositions() {
        return robotConfiguration.values().stream().toList();
    }

    @Override
    public List<Robot<StandardState>> getSignalingRobotsInRange(Robot<StandardState> robot, String condition, double distance) {
        PlainLocation robotPosition = getRobotPosition(robot);
        return robotConfiguration.entrySet().stream()
            .filter(e -> !e.getKey().equals(robot))
            .filter(e -> e.getValue().distanceFrom(robotPosition) <= distance)
            .filter(e -> e.getKey().getNavigationState().getCondition().getValue().equals(condition))
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
    }

    @Override
    public List<PlainLocation> getPositionsOf(List<Robot<StandardState>> robots) {
        List<PlainLocation> positions = new ArrayList<>();
        for (Robot<StandardState> r:
             robots) {
            positions.add(robotConfiguration.get(r));
        }
        return positions;
    }

    @Override
    public PlainLocation getSignalingAreaPosition(SignalingArea<PlainLocation> area) {
        if (!signalingAreas.contains(area))
            throw new IllegalArgumentException("no such area present in this environment.");
        return signalingAreas.stream().filter(a -> a.equals(area))
            .map(SignalingArea::getCenterPosition)
            .findFirst()
            .orElseThrow();
    }

    @Override
    public List<PlainLocation> getSignalingAreaPositions() {
        return signalingAreas.stream().map(SignalingArea::getCenterPosition).collect(Collectors.toList());
    }

    @Override
    public List<Robot<StandardState>> getRobotsInArea(SignalingArea<PlainLocation> area) {
        return robotConfiguration.entrySet().stream()
            .filter(e -> area.contains(e.getValue()))
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
    }

    @Override
    public List<SignalingArea<PlainLocation>> getAreasOccupiedBy(Robot<StandardState> robot) {
        return signalingAreas.stream()
            .filter(a -> a.contains(getRobotPosition(robot)))
            .collect(Collectors.toList());
    }

    @Override
    public void updateRobotState(Robot<StandardState> robot, StandardState state) {
        getRobot(robot.getId()).updateNavigationState(state);
    }

    @Override
    public Environment<StandardState, PlainLocation> evolve(Map<Robot<StandardState>, PlainLocation> updatedConfiguration) {
            return new PlainEnvironment(Objects.requireNonNull(updatedConfiguration), List.copyOf(signalingAreas));
    }
}

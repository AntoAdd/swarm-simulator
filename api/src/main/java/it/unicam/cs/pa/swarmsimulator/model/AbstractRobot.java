package it.unicam.cs.pa.swarmsimulator.model;

import java.util.Objects;

public class AbstractRobot<S extends RobotState> implements Robot<S>{
    private final long id;
    private S navigationState;

    public AbstractRobot(long id) {
        this.id = id;
    }


    @Override
    public long getId() {
        return id;
    }

    @Override
    public S getNavigationState() {
        return navigationState;
    }

    @Override
    public void updateNavigationState(S s) {
        navigationState = s;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractRobot<?> that = (AbstractRobot<?>) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

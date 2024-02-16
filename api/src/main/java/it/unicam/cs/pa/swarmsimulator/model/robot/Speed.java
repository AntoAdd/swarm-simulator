package it.unicam.cs.pa.swarmsimulator.model.robot;

public class Speed extends StateComponent<Double> {
    public Speed(Double value) {
        super(checkValue(value));
    }

    private static Double checkValue(Double value) {
        if (value < 0.0)
            throw new IllegalArgumentException("speed value cannot be negative.");
        return value;
    }
}

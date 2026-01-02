package com.replayEventDrivenSystem.state;

public class StateSnapshot {
    private final double value;

    public StateSnapshot(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}

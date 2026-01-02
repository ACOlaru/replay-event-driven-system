package com.replayEventDrivenSystem.replay;

import com.replayEventDrivenSystem.domain.Event;
import com.replayEventDrivenSystem.state.StateEngine;

import java.time.Duration;
import java.time.Instant;

public class AcceleratedTimeReplayStrategy implements ReplayStrategy {
    private Instant lastTimestamp = null;
    private double accelerationFactor = 0.3;

    @Override
    public void apply(Event event, StateEngine stateEngine) {
        if (lastTimestamp != null) {
            long delay = Duration.between(lastTimestamp, Instant.ofEpochSecond(event.timestamp())).toMillis();
            if (delay > 0) {
                try {
                    Thread.sleep((long) (delay * accelerationFactor));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        stateEngine.applyEvent(event);
        lastTimestamp = Instant.ofEpochSecond(event.timestamp());
    }

    public void setAccelerationFactor(double accelerationFactor) {
        this.accelerationFactor = accelerationFactor;
    }
}

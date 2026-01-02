package com.replayEventDrivenSystem.replay;

import com.replayEventDrivenSystem.domain.Event;
import com.replayEventDrivenSystem.state.StateEngine;

import java.time.Duration;
import java.time.Instant;

public class RealTimeReplayStrategy implements ReplayStrategy {
    private Instant lastTimestamp = null;

    @Override
    public void apply(Event event, StateEngine stateEngine) {
        if (lastTimestamp != null) {
            long delay = Duration.between(lastTimestamp, Instant.ofEpochSecond(event.timestamp())).toMillis();
            if (delay > 0) {
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        stateEngine.applyEvent(event);
        lastTimestamp = Instant.ofEpochSecond(event.timestamp());
    }
}

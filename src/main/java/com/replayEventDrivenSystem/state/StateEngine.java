package com.replayEventDrivenSystem.state;

import com.replayEventDrivenSystem.domain.Event;

public class StateEngine {
    private double value = 0;

    public void applyEvent(Event event) {
        switch (event.eventType()) {
            case SET -> applySet(event);
            case DECREMENT -> applyDecrement(event);
            case INCREMENT -> applyIncrement(event);
        }
    }

    private void applyIncrement(Event event) {
        value += event.payload().value();
    }

    private void applyDecrement(Event event) {
        value -= event.payload().value();
    }

    private void applySet(Event event) {
        value = event.payload().value();
    }

    public StateSnapshot getStateSnapshot() {
        return new StateSnapshot(value);
    }
}

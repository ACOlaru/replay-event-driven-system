package com.replayEventDrivenSystem.replay;

import com.replayEventDrivenSystem.domain.Event;
import com.replayEventDrivenSystem.state.StateEngine;

public class ImmediateReplayStrategy implements ReplayStrategy {
    @Override
    public void apply(Event event, StateEngine stateEngine) {
        stateEngine.applyEvent(event);
    }
}

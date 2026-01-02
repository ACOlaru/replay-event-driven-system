package com.replayEventDrivenSystem.replay;

import com.replayEventDrivenSystem.domain.Event;
import com.replayEventDrivenSystem.state.StateEngine;

public interface ReplayStrategy {
    void apply(Event event, StateEngine stateEngine);
}

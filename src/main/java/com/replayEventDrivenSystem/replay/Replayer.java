package com.replayEventDrivenSystem.replay;

import com.replayEventDrivenSystem.domain.Event;
import com.replayEventDrivenSystem.recording.EventLog;
import com.replayEventDrivenSystem.state.StateEngine;
import com.replayEventDrivenSystem.state.StateSnapshot;

public class Replayer {
    private final EventLog eventLog;
    private final ReplayStrategy replayStrategy;

    public Replayer(EventLog eventLog, ReplayStrategy replayStrategy) {
        this.eventLog = eventLog;
        this.replayStrategy = replayStrategy;
    }

    public StateSnapshot replay() {
        StateEngine stateEngine = new StateEngine();
        for (Event event : eventLog.getEvents()) {
            replayStrategy.apply(event, stateEngine);
        }
        return stateEngine.getStateSnapshot();
    }
}

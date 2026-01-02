package com.replayEventDrivenSystem.validation;

import com.replayEventDrivenSystem.recording.Recorder;
import com.replayEventDrivenSystem.replay.Replayer;

public class Validator {
    private final Recorder recorder;
    private final Replayer replayer;

    public Validator(Recorder recorder, Replayer replayer) {
        this.recorder = recorder;
        this.replayer = replayer;
    }

    public boolean validate() {
        return recorder.getStateEngine().getStateSnapshot().getValue() == replayer.replay().getValue();
    }
}

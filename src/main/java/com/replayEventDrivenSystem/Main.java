package com.replayEventDrivenSystem;

import com.replayEventDrivenSystem.app.EventSource;
import com.replayEventDrivenSystem.app.ProducerQueue;
import com.replayEventDrivenSystem.recording.EventLog;
import com.replayEventDrivenSystem.recording.Recorder;
import com.replayEventDrivenSystem.replay.ImmediateReplayStrategy;
import com.replayEventDrivenSystem.replay.ReplayStrategy;
import com.replayEventDrivenSystem.replay.Replayer;
import com.replayEventDrivenSystem.state.StateEngine;
import com.replayEventDrivenSystem.state.StateSnapshot;
import com.replayEventDrivenSystem.validation.Validator;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        ProducerQueue producerQueue = new ProducerQueue();
        EventLog eventLog = new EventLog();
        StateEngine recordingStateEngine = new StateEngine();

        Recorder recorder = new Recorder(eventLog, producerQueue, recordingStateEngine);
        Thread recorderThread = new Thread(recorder, "recorder-thread");
        recorderThread.start();

        int numberOfProducers = 3;
        List<EventSource> sources = new ArrayList<>();
        List<Thread> sourceThreads = new ArrayList<>();

        for (int i = 0; i < numberOfProducers; i++) {
            EventSource source = new EventSource(producerQueue);
            Thread t = new Thread(source, "producer-" + i);
            sources.add(source);
            sourceThreads.add(t);
            t.start();
        }

        Thread.sleep(3_000);

        for (EventSource source : sources) {
            source.stop();
        }
        for (Thread t : sourceThreads) {
            t.join();
        }

        recorder.stop();
        recorderThread.join();

        StateSnapshot recordedSnapshot = recordingStateEngine.getStateSnapshot();

        ReplayStrategy strategy = new ImmediateReplayStrategy();
        Replayer replayer = new Replayer(eventLog, strategy);
        StateSnapshot replayedSnapshot = replayer.replay();

        Validator validator = new Validator(recorder, replayer);
        boolean valid = validator.validate();

        System.out.println("Recorded snapshot : " + recordedSnapshot.getValue());
        System.out.println("Replayed snapshot : " + replayedSnapshot.getValue());
        System.out.println("Final state matches: " + valid);
    }
}
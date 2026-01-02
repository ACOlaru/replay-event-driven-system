package com.replayEventDrivenSystem.recording;

import com.replayEventDrivenSystem.app.ProducerQueue;
import com.replayEventDrivenSystem.domain.Event;
import com.replayEventDrivenSystem.state.StateEngine;

public class Recorder implements Runnable {
    private EventLog eventLog;
    private ProducerQueue producerQueue;
    private StateEngine stateEngine;
    private boolean running;

    public Recorder(EventLog eventLog, ProducerQueue producerQueue, StateEngine stateEngine) {
        this.eventLog = eventLog;
        this.producerQueue = producerQueue;
        this.stateEngine = stateEngine;
        this.running = true;
    }

    @Override
    public void run() {
        while (running || !producerQueue.isEmpty()) {
            drainQueueAndRecord();
            Thread.yield();
        }
    }

    public void stop() {
        running = false;
    }

    private void drainQueueAndRecord() {
        Event event;
        while (!producerQueue.isEmpty()) {
            event = producerQueue.poll();
            appendEvent(event);
            if (stateEngine != null) {
                stateEngine.applyEvent(event);
            }
        }
    }

    private void appendEvent(Event event) {
        eventLog.addEvent(event);
    }

    public StateEngine getStateEngine() {
        return stateEngine;
    }
}

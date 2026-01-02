package com.replayEventDrivenSystem.app;

import com.replayEventDrivenSystem.domain.Event;
import com.replayEventDrivenSystem.domain.EventIdGenerator;
import com.replayEventDrivenSystem.domain.EventPayload;
import com.replayEventDrivenSystem.domain.EventType;

import java.time.Instant;
import java.util.Random;

public class EventSource implements Runnable {

    private final ProducerQueue producerQueue;
    private final Random random = new Random();
    private volatile boolean running = true;

    public EventSource(ProducerQueue producerQueue) {
        this.producerQueue = producerQueue;
    }

    @Override
    public void run() {
        while (running) {
            Event event = generateEvent();
            producerQueue.processEvent(event);
            sleepQuietly(50);
        }
    }

    public void stop() {
        running = false;
    }

    private Event generateEvent() {
        EventType type = randomEventType();
        double value = random.nextDouble(10) + 1;

        EventPayload payload = new EventPayload(value);

        return new Event(
                EventIdGenerator.getNextId(),
                Instant.now().toEpochMilli(),
                type,
                payload
        );
    }

    private EventType randomEventType() {
        int pick = random.nextInt(3);
        return switch (pick) {
            case 0 -> EventType.INCREMENT;
            case 1 -> EventType.DECREMENT;
            default -> EventType.SET;
        };
    }

    private void sleepQuietly(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
        }
    }
}


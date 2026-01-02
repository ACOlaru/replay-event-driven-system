package com.replayEventDrivenSystem.app;

import com.replayEventDrivenSystem.domain.Event;

import java.util.concurrent.ConcurrentLinkedQueue;

public class ProducerQueue {
    private ConcurrentLinkedQueue<Event> queue;

    public ProducerQueue() {
        queue = new ConcurrentLinkedQueue<>();
    }

    public void processEvent(Event event) {
        queue.add(event);
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public Event poll() {
        return queue.poll();
    }
}

package com.replayEventDrivenSystem.domain;

import java.util.concurrent.atomic.AtomicLong;

public class EventIdGenerator {
    private static AtomicLong count = new AtomicLong(0);

    public static long getNextId() {
        return count.getAndIncrement();
    }
}

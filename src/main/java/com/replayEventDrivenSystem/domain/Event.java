package com.replayEventDrivenSystem.domain;

public record Event(
        long id,
        long timestamp,
        EventType eventType,
        EventPayload payload
) {
}

package com.replayEventDrivenSystem.recording;

import com.replayEventDrivenSystem.domain.Event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EventLog {
    private final List<Event> eventLog = new ArrayList<>();

    public void addEvent(Event event) {
        eventLog.add(event);
    }

    public List<Event> getEvents() {
        return Collections.unmodifiableList(eventLog);
    }
}

package com.bfjournal.coord.model;

import java.util.ArrayList;
import java.util.List;

public class EventsList {
    private List<Event> events = new ArrayList<>();

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
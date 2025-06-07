package io.github.oppapili.jostrel.service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.stereotype.Component;
import io.github.oppapili.jostrel.model.Event;

@Component
public class EventStore {
    private final List<Event> events = new CopyOnWriteArrayList<>();

    public void save(Event event) {
        events.add(event);
    }

    public List<Event> findAll() {
        return List.copyOf(events);
    }
}

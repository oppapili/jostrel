package io.github.oppapili.jostrel.repository;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.stereotype.Repository;
import io.github.oppapili.jostrel.model.Event;

@Repository
public class EventRepository {
    private final List<Event> events = new CopyOnWriteArrayList<>();

    public void save(Event event) {
        events.add(event);
    }

    public List<Event> findAll() {
        return List.copyOf(events);
    }
}

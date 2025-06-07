package io.github.oppapili.jostrel.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.github.oppapili.jostrel.model.Event;
import io.github.oppapili.jostrel.model.Filter;
import io.github.oppapili.jostrel.repository.EventRepository;

@Service
public class EventService {

    @Autowired
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public void saveEvent(Event event) {
        eventRepository.save(event);
    }

    public List<Event> findEventsByFilters(List<Filter> filters) {
        return eventRepository.findAll().stream()
                .filter(event -> filters.stream().anyMatch(filter -> matches(filter, event)))
                .toList();
    }

    private boolean matches(Filter filter, Event event) {
        // TODO: implement filter matching logic
        return true;
    }
}

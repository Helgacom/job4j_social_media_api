package ru.job4j.socialmedia.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.socialmedia.model.Event;
import ru.job4j.socialmedia.repository.EventRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleEventService implements EventService {

    private final EventRepository eventRepository;

    @Override
    public Event save(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public Optional<Event> findById(Long id) {
        return eventRepository.findById(id);
    }

    @Override
    public boolean deleteById(Long id) {
        return eventRepository.delete(id) > 0L;
    }

    @Override
    public List<Event> findAll() {
        return eventRepository.findAll();
    }
}

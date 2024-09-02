package ru.job4j.socialmedia.service;

import ru.job4j.socialmedia.model.Event;

import java.util.List;
import java.util.Optional;

public interface EventService {

    Event save(Event event);

    Optional<Event> findById(Long id);

    boolean deleteById(Long id);

    List<Event> findAll();
}

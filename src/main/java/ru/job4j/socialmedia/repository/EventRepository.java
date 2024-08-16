package ru.job4j.socialmedia.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.socialmedia.model.Event;

public interface EventRepository extends CrudRepository<Event, Long> {
}

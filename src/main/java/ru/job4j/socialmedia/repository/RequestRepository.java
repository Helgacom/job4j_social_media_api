package ru.job4j.socialmedia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.job4j.socialmedia.model.Request;

public interface RequestRepository extends JpaRepository<Request, Long> {
}

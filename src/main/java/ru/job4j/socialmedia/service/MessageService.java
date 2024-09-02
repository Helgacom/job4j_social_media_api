package ru.job4j.socialmedia.service;

import ru.job4j.socialmedia.model.Message;

import java.util.List;
import java.util.Optional;

public interface MessageService {

    Message save(Message message);

    Optional<Message> findById(Long id);

    boolean deleteById(Long id);

    List<Message> findAll();
}

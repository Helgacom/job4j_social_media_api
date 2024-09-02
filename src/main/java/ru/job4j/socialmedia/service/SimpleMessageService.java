package ru.job4j.socialmedia.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.socialmedia.model.Message;
import ru.job4j.socialmedia.repository.MessageRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleMessageService implements MessageService {

    private final MessageRepository messageRepository;

    @Override
    public Message save(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public Optional<Message> findById(Long id) {
        return messageRepository.findById(id);
    }

    @Override
    public boolean deleteById(Long id) {
        return messageRepository.delete(id) > 0L;
    }

    @Override
    public List<Message> findAll() {
        return messageRepository.findAll();
    }
}

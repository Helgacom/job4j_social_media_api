package ru.job4j.socialmedia.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.socialmedia.model.User;
import ru.job4j.socialmedia.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleUserService implements UserService {

    private final UserRepository userRepository;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public boolean update(User user) {
        return userRepository.update(user) > 0L;
    }

    @Override
    public boolean deleteById(Long userId) {
        return userRepository.delete(userId) > 0L;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        return userRepository.findByLoginAndPassword(login, password);
    }

    @Override
    public List<User> findAllSubscribersById(Long id) {
        return userRepository.findAllSubscribersById(id);
    }

    @Override
    public List<User> findAllFriendsById(Long id) {
        return userRepository.findAllFriendsById(id);
    }
}

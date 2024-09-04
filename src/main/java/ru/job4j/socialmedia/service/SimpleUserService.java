package ru.job4j.socialmedia.service;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.job4j.socialmedia.dto.UserDto;
import ru.job4j.socialmedia.mapper.UserMapper;
import ru.job4j.socialmedia.model.User;
import ru.job4j.socialmedia.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Validated
@Service
@AllArgsConstructor
public class SimpleUserService implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public boolean update(User user) {
        return userRepository.update(user) > 0L;
    }

    @Override
    public User create(@Valid UserDto user) {
        var newUser = userMapper.userDtoToUser(user);
        userRepository.save(newUser);
        return newUser;
    }

    @Override
    public boolean updateFromDto(@Valid UserDto user) {
        var newUser = userMapper.userDtoToUser(user);
        return userRepository.update(newUser) > 0L;
    }

    @Override
    public List<UserDto> findByUserIdsList(List<Long> userIds) {
        return userIds.stream().map(userRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(userMapper::userToUserDto)
                .toList();
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

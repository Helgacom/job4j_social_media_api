package ru.job4j.socialmedia.service;

import ru.job4j.socialmedia.dto.UserDto;
import ru.job4j.socialmedia.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User save(User user);

    boolean update(User user);

    Optional<User> findById(Long userId);

    boolean deleteById(Long userId);

    List<User> findAll();

    List<UserDto> findByUserIdsList(List<Long> userIds);

    Optional<User> findByLoginAndPassword(String login, String password);

    List<User> findAllSubscribersById(Long id);

    List<User> findAllFriendsById(Long id);
}

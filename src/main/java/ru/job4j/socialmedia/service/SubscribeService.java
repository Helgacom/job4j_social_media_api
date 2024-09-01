package ru.job4j.socialmedia.service;

import ru.job4j.socialmedia.model.Subscribe;
import ru.job4j.socialmedia.model.User;

import java.util.List;
import java.util.Optional;

public interface SubscribeService {

    Subscribe save(Subscribe subscribe);

    Optional<Subscribe> findById(Long id);

    boolean deleteById(Long id);

    List<Subscribe> findAll();

    void makeSubscribe(User userSubscriber, User userTo);

    void deleteSubscribe(User user, Subscribe subscribe);

    Optional<Subscribe> findSubscribeByUsersIds(Long userSubscriberId, Long userToId);
}

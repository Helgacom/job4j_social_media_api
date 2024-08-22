package ru.job4j.socialmedia.service;

import ru.job4j.socialmedia.model.Subscribe;
import ru.job4j.socialmedia.model.User;

import java.util.Optional;

public interface SubscribeService {

    void makeSubscribe(User userSubscriber, User userTo);

    void deleteSubscribe(User user, Subscribe subscribe);

    Optional<Subscribe> findSubscribeByUsersIds(Long userSubscriberId, Long userToId);
}

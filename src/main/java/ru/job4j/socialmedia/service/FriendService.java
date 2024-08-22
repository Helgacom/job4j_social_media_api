package ru.job4j.socialmedia.service;

import ru.job4j.socialmedia.model.Friend;
import ru.job4j.socialmedia.model.Request;
import ru.job4j.socialmedia.model.User;

import java.util.List;
import java.util.Optional;

public interface FriendService {

    Friend save(Friend friend);

    Optional<Friend> findById(Long id);

    boolean deleteById(Long id);

    List<Friend> findAll();

    void makeFriend(User user, User friend);

    void makeFriendAndSubscribesFromRequest(Request request);

    void deleteFriend(User user, Friend friend);
}

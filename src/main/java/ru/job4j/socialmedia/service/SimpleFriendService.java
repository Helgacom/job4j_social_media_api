package ru.job4j.socialmedia.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.socialmedia.mapper.*;
import ru.job4j.socialmedia.model.Friend;
import ru.job4j.socialmedia.model.Request;
import ru.job4j.socialmedia.model.User;
import ru.job4j.socialmedia.repository.FriendRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleFriendService implements FriendService {

    private final FriendRepository friendRepository;
    private final SubscribeService subscribeService;
    private final FriendSubscribesMapper friendSubscribesMapper;

    @Override
    public Friend save(Friend friend) {
        return friendRepository.save(friend);
    }

    @Override
    public Optional<Friend> findById(Long id) {
        return friendRepository.findById(id);
    }

    @Override
    public boolean deleteById(Long id) {
        return friendRepository.delete(id) > 0L;
    }

    @Override
    public List<Friend> findAll() {
        return friendRepository.findAll();
    }

    @Transactional
    @Override
    public void makeFriend(User userOffer, User userAccept) {
        var newFriend = new Friend();
        newFriend.setUser(userOffer);
        newFriend.setFriend(userAccept);
        friendRepository.save(newFriend);
        subscribeService.makeSubscribe(userOffer, userAccept);
        if (newFriend.isStatus()) {
            subscribeService.makeSubscribe(userAccept, userOffer);
        }
    }

    @Transactional
    @Override
    public void makeFriendAndSubscribesFromRequest(Request request) {
        var dto = friendSubscribesMapper.getFriendSubscribesDtoFromRequest(request);
        var newFriend = dto.getFriend();
        var subscriberOffer = dto.getOffer();
        var subscriberAccept = dto.getAccept();
        friendRepository.save(newFriend);
        subscribeService.save(subscriberOffer);
        if (request.isAccept()) {
            subscribeService.save(subscriberAccept);
        }
    }

    @Transactional
    @Override
    public void deleteFriend(User user, Friend friend) {
        if (friendRepository.findFriendByUserId(user.getId()).contains(friend)) {
            var subscribe = subscribeService
                    .findSubscribeByUsersIds(friend.getFriend().getId(), friend.getUser().getId()).get();
            subscribeService.deleteSubscribe(user, subscribe);
            friendRepository.delete(friend);
        }
    }
}

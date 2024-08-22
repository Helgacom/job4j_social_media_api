package ru.job4j.socialmedia.mapper;

import org.mapstruct.Named;
import org.springframework.stereotype.Component;
import ru.job4j.socialmedia.model.Friend;
import ru.job4j.socialmedia.model.Request;
import ru.job4j.socialmedia.model.Subscribe;

@Named("FriendSubscribeMapperUtil")
@Component
public class FriendSubscribeMapperUtil {

    @Named("createFriendFromRequest")
    public Friend createFriendFromRequest(Request request) {
        var newFriend = new Friend();
        newFriend.setUser(request.getUserFrom());
        newFriend.setFriend(request.getUserTo());
        if (request.isAccept()) {
            newFriend.setStatus(true);
        }
        return newFriend;
    }

    @Named("createSubscribeFromRequest")
    public Subscribe createSubscribeFromRequest(Request request) {
        var newSubscribe = new Subscribe();
        newSubscribe.setUserSubscriber(request.getUserFrom());
        newSubscribe.setUserTo(request.getUserTo());
        return newSubscribe;
    }

    @Named("createSubscribeFromAcceptedRequest")
    public Subscribe createSubscribeFromAcceptedRequest(Request request) {
        var newSubscribe = new Subscribe();
        newSubscribe.setUserSubscriber(request.getUserTo());
        newSubscribe.setUserTo(request.getUserFrom());
        return newSubscribe;
    }
}

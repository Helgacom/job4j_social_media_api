package ru.job4j.socialmedia.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import ru.job4j.socialmedia.dto.FriendSubscribesDto;
import ru.job4j.socialmedia.model.Request;

@Mapper(componentModel = "spring",
        uses = {RequestMapper.class, FriendSubscribeMapperUtil.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
@Component
public interface FriendSubscribesMapper {

    @Mapping(target = "friend",
            qualifiedByName = {"FriendSubscribeMapperUtil", "createFriendFromRequest"}, source = "request")
    @Mapping(target = "offer",
            qualifiedByName = {"FriendSubscribeMapperUtil", "createSubscribeFromRequest"}, source = "request")
    @Mapping(target = "accept",
            qualifiedByName = {"FriendSubscribeMapperUtil", "createSubscribeFromAcceptedRequest"}, source = "request")
    FriendSubscribesDto getFriendSubscribesDtoFromRequest(Request request);
}

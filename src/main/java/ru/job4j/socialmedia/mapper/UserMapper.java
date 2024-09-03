package ru.job4j.socialmedia.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import ru.job4j.socialmedia.dto.UserDto;
import ru.job4j.socialmedia.model.User;

@Mapper(componentModel = "spring",
        uses = PostListMapperUtil.class,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
@Component
public interface UserMapper {

    @Mapping(target = "posts", qualifiedByName = {"PostListMapperUtil", "findPostsByUserId"}, source = "user.id")
    UserDto userToUserDto(User user);

    User userDtoToUser(UserDto userDto);
}

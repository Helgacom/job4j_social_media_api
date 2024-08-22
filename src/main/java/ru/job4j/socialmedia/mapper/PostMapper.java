package ru.job4j.socialmedia.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import ru.job4j.socialmedia.dto.PostDto;
import ru.job4j.socialmedia.model.Post;

@Mapper(componentModel = "spring",
        uses = {FileMapper.class, FileListMapperUtil.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
@Component
public interface PostMapper {

    @Mapping(target = "fileList", qualifiedByName = {"FileListMapperUtil", "findFilesByPostId"}, source = "post.id")
    PostDto getPostDtoFromPost(Post post);

    Post getPostFromPostDto(PostDto postDto);
}

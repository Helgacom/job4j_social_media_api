package ru.job4j.socialmedia.mapper;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;
import ru.job4j.socialmedia.dto.PostDto;
import ru.job4j.socialmedia.repository.PostRepository;

import java.util.List;

@Named("PostListMapperUtil")
@Component
@RequiredArgsConstructor
public class PostListMapperUtil {

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    @Named("findPostsByUserId")
    public List<PostDto> findPostsByUserId(Long id) {
        return postMapper.getPostDtoListFromPostList(postRepository.findByUserId(id));
    }
}

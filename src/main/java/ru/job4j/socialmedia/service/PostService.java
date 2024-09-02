package ru.job4j.socialmedia.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.job4j.socialmedia.dto.PostDto;
import ru.job4j.socialmedia.model.Post;
import ru.job4j.socialmedia.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PostService {

    Post save(Post post);

    Post create(PostDto post);

    boolean update(Post post);

    boolean updateFromDto(PostDto post);

    Optional<Post> findById(Long id);

    boolean deleteById(Long id);

    List<Post> findAll();

    List<Post> findByUserId(Long id);

    List<Post> findByToday(LocalDateTime today);

    Page<Post> findByOrderByCreated(Pageable pageable);

    boolean updateTitleAndText(String title, String text, Long id);

    boolean deleteFileByPostId(Long id);

    Page<Post> findAllSubscriberPostsByOrderDescWithPagination(Long id, Pageable pageable);

    boolean deletePostByUser(User user, Post post);

    boolean updatePostByUser(User user, Post post);
}

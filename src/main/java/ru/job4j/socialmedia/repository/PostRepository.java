package ru.job4j.socialmedia.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.socialmedia.model.Post;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends CrudRepository<Post, Long> {

    List<Post> findByUserId(Long id);

    List<Post> findByCreatedGreaterThanEqual(LocalDateTime today);

    Page<Post> findByOrderByCreated(Pageable pageable);
}

package ru.job4j.socialmedia.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.socialmedia.model.Post;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByUserId(Long id);

    List<Post> findByCreatedGreaterThanEqual(LocalDateTime today);

    Page<Post> findByOrderByCreated(Pageable pageable);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("""
            update Post post set post.title = :title, post.text = :text
            where post.id = :id
            """)
    int updateTitleAndText(@Param("title") String title, @Param("text") String text, @Param("id") Long id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("""
            delete File as f
            cross join Post as post
            where f.post.id = :id
            """)
    int deleteFileByPostId(@Param("id") Long id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("""
            delete Post as post where post.id = :id
            """)
    int deletePostById(@Param("id") Long id);

    @Query("""
            select post from Post as post
            join User as u on u.id = post.user.id
            join Subscribe s on u.id = s.userTo.id
            where s.userSubscriber.id = :id
            order by post.created desc
            """)
    Page<Post> findAllSubscriberPostsByOrderDescWithPagination(@Param("id") Long id, Pageable pageable);
}

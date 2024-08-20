package ru.job4j.socialmedia.repository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import ru.job4j.socialmedia.model.Post;
import ru.job4j.socialmedia.model.User;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @AfterAll
    public void clearAll() {
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void whenSavePostThenFindById() {
        var post = new Post();
        var user = new User();
        user.setName("testName");
        user.setLogin("test@example.com");
        user.setPassword("123t");
        userRepository.save(user);
        post.setTitle("testTitle");
        post.setText("testText");
        post.setUser(user);
        postRepository.save(post);
        var foundPost = postRepository.findById(post.getId());
        assertThat(foundPost).isPresent();
        assertThat(foundPost.get().getTitle()).isEqualTo("testTitle");
    }

    @Test
    public void whenFindByUserIdThenFindPostsList() {
        var user = new User();
        user.setName("testName");
        user.setLogin("test@example.com");
        user.setPassword("123t");
        userRepository.save(user);
        var post1 = new Post();
        post1.setTitle("testTitle1");
        post1.setText("testText1");
        post1.setUser(user);
        var post2 = new Post();
        post2.setTitle("testTitle2");
        post2.setText("testText2");
        post2.setUser(user);
        postRepository.save(post1);
        postRepository.save(post2);
        var posts = postRepository.findByUserId(user.getId());
        assertThat(posts).hasSize(2);
        assertThat(posts).containsExactlyInAnyOrder(post1, post2);
    }

    @Test
    public void whenFindByOrderByCreatedThanGetPostsPage() {
        var user = new User();
        user.setName("testName");
        user.setLogin("test@example.com");
        user.setPassword("123t");
        userRepository.save(user);
        var post1 = new Post();
        post1.setTitle("testTitle1");
        post1.setText("testText1");
        post1.setUser(user);
        var post2 = new Post();
        post2.setTitle("testTitle2");
        post2.setText("testText2");
        post2.setUser(user);
        postRepository.save(post1);
        postRepository.save(post2);
        assertThat(postRepository.findByOrderByCreated(Pageable.ofSize(1))
                .getTotalElements()).isEqualTo(2);
    }

    @Test
    public void whenFindByCreatedThenFindPostsList() {
        var user = new User();
        user.setName("testName");
        user.setLogin("test@example.com");
        user.setPassword("123t");
        userRepository.save(user);
        var post1 = new Post();
        post1.setTitle("testTitle1");
        post1.setText("testText1");
        post1.setUser(user);
        var post2 = new Post();
        post2.setTitle("testTitle2");
        post2.setText("testText2");
        post2.setUser(user);
        postRepository.save(post1);
        postRepository.save(post2);
        LocalDateTime today = LocalDateTime.now().minusDays(1).truncatedTo(ChronoUnit.DAYS);
        assertThat(postRepository.findByCreatedGreaterThanEqual(today))
                .hasSize(2)
                .containsExactlyInAnyOrder(post1, post2);
    }

    @Test
    public void whenFindAllThenReturnAllPosts() {
        var user1 = new User();
        user1.setName("testName1");
        user1.setLogin("test1@example.com");
        user1.setPassword("123t1");
        var user2 = new User();
        user2.setName("testName2");
        user2.setLogin("test2@example.com");
        user2.setPassword("123t2");
        userRepository.save(user1);
        userRepository.save(user2);
        var post1 = new Post();
        post1.setTitle("testTitle1");
        post1.setText("testText1");
        post1.setUser(user1);
        var post2 = new Post();
        post2.setTitle("testTitle2");
        post2.setText("testText2");
        post2.setUser(user2);
        postRepository.save(post1);
        postRepository.save(post2);
        var posts = postRepository.findAll();
        assertThat(posts).hasSize(2);
        assertThat(posts).extracting(Post::getTitle).contains("testTitle1", "testTitle2");
    }

    @Test
    public void whenDeletePostThenPostNotFound() {
        var post = new Post();
        var user = new User();
        user.setName("testName");
        user.setLogin("test@example.com");
        user.setPassword("123t");
        userRepository.save(user);
        post.setTitle("testTitle");
        post.setText("testText");
        post.setUser(user);
        postRepository.save(post);
        postRepository.delete(post);
        var foundPost = postRepository.findById(post.getId());
        assertThat(foundPost).isNotPresent();
        assertThat(postRepository.findAll()).doesNotContain(post);
    }
}
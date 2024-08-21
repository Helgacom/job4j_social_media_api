package ru.job4j.socialmedia.repository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import ru.job4j.socialmedia.model.File;
import ru.job4j.socialmedia.model.Post;
import ru.job4j.socialmedia.model.Subscribe;
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

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private SubscribeRepository subscribeRepository;

    @BeforeEach
    public void setUp() {
        subscribeRepository.deleteAll();
        postRepository.deleteAll();
        fileRepository.deleteAll();
        userRepository.deleteAll();
    }

    @AfterAll
    public void clearAll() {
        subscribeRepository.deleteAll();
        postRepository.deleteAll();
        fileRepository.deleteAll();
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

    @Test
    public void whenDeletePostByIdThenPostNotFound() {
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
        var id = post.getId();
        postRepository.deletePostById(id);
        var foundPost = postRepository.findById(id);
        assertThat(foundPost).isNotPresent();
        assertThat(postRepository.findAll()).doesNotContain(post);
    }

    @Test
    public void whenDeleteFileByPostIdThenFileNotFound() {
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
        var file = new File();
        file.setName("testName");
        file.setPath("test/");
        file.setPost(post);
        fileRepository.save(file);
        var id = file.getId();
        postRepository.deleteFileByPostId(post.getId());
        var foundFile = fileRepository.findById(id);
        assertThat(foundFile).isEmpty();
    }

    @Test
    public void whenUpdateTitleAndTextThenFindUpdatedPost() {
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
        var id = post.getId();
        postRepository.updateTitleAndText("NewTitle", "NewText", id);
        var updatedPost = postRepository.findById(id);
        assertThat(updatedPost).isPresent();
        assertThat(updatedPost.get().getTitle()).isEqualTo("NewTitle");
        assertThat(updatedPost.get().getText()).isEqualTo("NewText");
    }

    @Test
    public void whenFindAllSubscriberPostsByOrderDescWithPaginationThenReturnPostPages() {
        var userSubscriber = new User();
        userSubscriber.setName("testName1");
        userSubscriber.setLogin("test1@example.com");
        userSubscriber.setPassword("123t1");
        var userTo1 = new User();
        userTo1.setName("testName2");
        userTo1.setLogin("test2@example.com");
        userTo1.setPassword("123t2");
        var userTo2 = new User();
        userTo2.setName("testName3");
        userTo2.setLogin("test3@example.com");
        userTo2.setPassword("123t3");
        userRepository.save(userSubscriber);
        userRepository.save(userTo1);
        userRepository.save(userTo2);
        var subscribe1 = new Subscribe();
        subscribe1.setUserSubscriber(userSubscriber);
        subscribe1.setUserTo(userTo1);
        var subscribe2 = new Subscribe();
        subscribe2.setUserSubscriber(userSubscriber);
        subscribe2.setUserTo(userTo2);
        subscribeRepository.save(subscribe1);
        subscribeRepository.save(subscribe2);
        var post1 = new Post();
        post1.setTitle("testTitle1");
        post1.setText("testText1");
        post1.setUser(userTo1);
        var post2 = new Post();
        post2.setTitle("testTitle2");
        post2.setText("testText2");
        post2.setUser(userTo2);
        postRepository.save(post1);
        postRepository.save(post2);
        var posts = postRepository
                .findAllSubscriberPostsByOrderDescWithPagination(userSubscriber.getId(), Pageable.ofSize(1));
        assertThat(posts.getTotalElements()).isEqualTo(2);
    }
}
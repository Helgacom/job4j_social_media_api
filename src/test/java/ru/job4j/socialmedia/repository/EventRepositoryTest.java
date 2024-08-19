package ru.job4j.socialmedia.repository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.socialmedia.model.Event;
import ru.job4j.socialmedia.model.Post;
import ru.job4j.socialmedia.model.User;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EventRepositoryTest {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        eventRepository.deleteAll();
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @AfterAll
    public void clearAll() {
        eventRepository.deleteAll();
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void whenSaveEventThenFindById() {
        var event = new Event();
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
        event.setUser(user);
        event.setPost(post);
        eventRepository.save(event);
        var foundEvent = eventRepository.findById(event.getId());
        assertThat(foundEvent).isPresent();
        assertThat(foundEvent.get().getPost().getTitle()).isEqualTo("testTitle");
        assertThat(foundEvent.get().getUser().getName()).isEqualTo("testName");
    }

    @Test
    public void whenFindAllThenReturnAllEvents() {
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
        var event1 = new Event();
        event1.setUser(user1);
        event1.setPost(post1);
        var event2 = new Event();
        event2.setUser(user2);
        event2.setPost(post2);
        eventRepository.save(event1);
        eventRepository.save(event2);
        var events = eventRepository.findAll();
        assertThat(events).hasSize(2);
        assertThat(events).extracting(Event::getUser).contains(user1, user2);
        assertThat(events).extracting(Event::getPost).contains(post1, post2);
    }

    @Test
    public void whenDeleteEventThenEventNotFound() {
        var event = new Event();
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
        event.setUser(user);
        event.setPost(post);
        eventRepository.save(event);
        eventRepository.delete(event);
        var foundEvent = eventRepository.findById(event.getId());
        assertThat(foundEvent).isNotPresent();
        assertThat(eventRepository.findAll()).doesNotContain(event);
    }
}
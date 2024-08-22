package ru.job4j.socialmedia.repository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.socialmedia.model.Subscribe;
import ru.job4j.socialmedia.model.User;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SubscribeRepositoryTest {

    @Autowired
    private SubscribeRepository subscribeRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        subscribeRepository.deleteAll();
        userRepository.deleteAll();
    }

    @AfterAll
    public void clearAll() {
        subscribeRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void whenSaveSubscribeThenFindById() {
        var userSubscriber = new User();
        userSubscriber.setName("testName1");
        userSubscriber.setLogin("test1@example.com");
        userSubscriber.setPassword("123t1");
        var userTo = new User();
        userTo.setName("testName2");
        userTo.setLogin("test2@example.com");
        userTo.setPassword("123t2");
        userRepository.save(userSubscriber);
        userRepository.save(userTo);
        var subscribe = new Subscribe();
        subscribe.setUserSubscriber(userSubscriber);
        subscribe.setUserTo(userTo);
        subscribeRepository.save(subscribe);
        var foundSubscribe = subscribeRepository.findById(subscribe.getId());
        assertThat(foundSubscribe).isPresent();
        assertThat(foundSubscribe.get().getUserSubscriber().getName()).isEqualTo("testName1");
        assertThat(foundSubscribe.get().getUserTo().getName()).isEqualTo("testName2");
    }

    @Test
    public void whenFindAllThenReturnAllSubscribes() {
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
        var subscribes = subscribeRepository.findAll();
        assertThat(subscribes).hasSize(2);
        assertThat(subscribes).containsExactlyInAnyOrder(subscribe1, subscribe2);
    }

    @Test
    public void whenDeleteSubscribeThenMessageNotFound() {
        var userSubscriber = new User();
        userSubscriber.setName("testName1");
        userSubscriber.setLogin("test1@example.com");
        userSubscriber.setPassword("123t1");
        var userTo = new User();
        userTo.setName("testName2");
        userTo.setLogin("test2@example.com");
        userTo.setPassword("123t2");
        userRepository.save(userSubscriber);
        userRepository.save(userTo);
        var subscribe = new Subscribe();
        subscribe.setUserSubscriber(userSubscriber);
        subscribe.setUserTo(userTo);
        subscribeRepository.save(subscribe);
        subscribeRepository.delete(subscribe);
        var foundSubscribe = subscribeRepository.findById(subscribe.getId());
        assertThat(foundSubscribe).isNotPresent();
        assertThat(subscribeRepository.findAll()).doesNotContain(subscribe);
    }

    @Test
    public void whenFindByUserSubscriberIdThenReturnSubscribesList() {
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
        var subList = subscribeRepository.findByUserSubscriberId(userSubscriber.getId());
        assertThat(subList).hasSize(2)
                .containsExactlyInAnyOrder(subscribe1, subscribe2);
    }

    @Test
    public void whenFindSubscribeByUsersIdsThenReturnSubscribe() {
        var userSubscriber = new User();
        userSubscriber.setName("testName1");
        userSubscriber.setLogin("test1@example.com");
        userSubscriber.setPassword("123t1");
        var userTo = new User();
        userTo.setName("testName2");
        userTo.setLogin("test2@example.com");
        userTo.setPassword("123t2");
        userRepository.save(userSubscriber);
        userRepository.save(userTo);
        var subscribe = new Subscribe();
        subscribe.setUserSubscriber(userSubscriber);
        subscribe.setUserTo(userTo);
        subscribeRepository.save(subscribe);
        var usersSubscribe = subscribeRepository.findSubscribeByUserSubscriberAndUserToId(userSubscriber.getId(), userTo.getId());
        assertThat(usersSubscribe).isPresent();
        assertThat(usersSubscribe.get()).isEqualTo(subscribe);
    }
}
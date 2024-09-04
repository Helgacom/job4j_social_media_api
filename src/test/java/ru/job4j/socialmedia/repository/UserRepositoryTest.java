package ru.job4j.socialmedia.repository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.socialmedia.model.Friend;
import ru.job4j.socialmedia.model.Subscribe;
import ru.job4j.socialmedia.model.User;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private SubscribeRepository subscribeRepository;

    @BeforeEach
    public void setUp() {
        friendRepository.deleteAll();
        subscribeRepository.deleteAll();
        userRepository.deleteAll();
    }

    @AfterAll
    public void clearAll() {
        friendRepository.deleteAll();
        subscribeRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void whenSaveUserThenFindById() {
        var user = new User();
        user.setName("testName");
        user.setLogin("test@ex.com");
        user.setPassword("123t");
        userRepository.save(user);
        var foundUser = userRepository.findById(user.getId());
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getName()).isEqualTo("testName");
    }

    @Test
    public void whenFindAllThenReturnAllUsers() {
        var user1 = new User();
        user1.setName("testName1");
        user1.setLogin("test1@ex.com");
        user1.setPassword("123t1");
        var user2 = new User();
        user2.setName("testName2");
        user2.setLogin("test2@ex.com");
        user2.setPassword("123t2");
        userRepository.save(user1);
        userRepository.save(user2);
        var users = userRepository.findAll();
        assertThat(users).hasSize(2);
        assertThat(users).extracting(User::getName).contains("testName1", "testName2");
    }

    @Test
    public void whenDeleteUserThenUserNotFound() {
        var user = new User();
        user.setName("testName");
        user.setLogin("test@ex.com");
        user.setPassword("123t");
        userRepository.save(user);
        userRepository.delete(user);
        var foundUser = userRepository.findById(user.getId());
        assertThat(foundUser).isNotPresent();
        assertThat(userRepository.findAll()).doesNotContain(user);
    }

    @Test
    public void whenFindByLoginAndPasswordThenFindUser() {
        var user = new User();
        user.setName("testName");
        user.setLogin("test@ex.com");
        user.setPassword("123t");
        userRepository.save(user);
        var foundUser = userRepository.findByLoginAndPassword("test@ex.com", "123t");
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getName()).isEqualTo("testName");
    }

    @Test
    public void whenFindAllFriendsByIdThenReturnUserFriends() {
        var userOffer = new User();
        userOffer.setName("testName1");
        userOffer.setLogin("test1@ex.com");
        userOffer.setPassword("123t1");
        var userAccept1 = new User();
        userAccept1.setName("testName2");
        userAccept1.setLogin("test2@ex.com");
        userAccept1.setPassword("123t2");
        var userAccept2 = new User();
        userAccept2.setName("testName3");
        userAccept2.setLogin("test3@ex.com");
        userAccept2.setPassword("123t3");
        userRepository.save(userOffer);
        userRepository.save(userAccept1);
        userRepository.save(userAccept2);
        var friend1 = new Friend();
        friend1.setUser(userOffer);
        friend1.setFriend(userAccept1);
        friend1.setStatus(true);
        var friend2 = new Friend();
        friend2.setUser(userOffer);
        friend2.setFriend(userAccept2);
        friend2.setStatus(true);
        friendRepository.save(friend1);
        friendRepository.save(friend2);
        var userFriends = userRepository.findAllFriendsById(userOffer.getId());
        assertThat(userFriends).hasSize(2);
        assertThat(userFriends).containsExactlyInAnyOrder(userAccept1, userAccept2);
    }

    @Test
    public void whenFindAllSubscribersByIdThenReturnAllSubscribers() {
        var userSubscriber = new User();
        userSubscriber.setName("testName1");
        userSubscriber.setLogin("test1@ex.com");
        userSubscriber.setPassword("123t1");
        var userTo = new User();
        userTo.setName("testName2");
        userTo.setLogin("test2@ex.com");
        userTo.setPassword("123t2");
        userRepository.save(userSubscriber);
        userRepository.save(userTo);
        var subscribe = new Subscribe();
        subscribe.setUserSubscriber(userSubscriber);
        subscribe.setUserTo(userTo);
        subscribeRepository.save(subscribe);
        var userSubscribes = userRepository.findAllSubscribersById(userTo.getId());
        assertThat(userSubscribes).hasSize(1);
        assertThat(userSubscribes).containsExactlyInAnyOrder(userSubscriber);
    }
}
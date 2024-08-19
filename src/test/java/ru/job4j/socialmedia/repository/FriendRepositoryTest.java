package ru.job4j.socialmedia.repository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.socialmedia.model.Friend;
import ru.job4j.socialmedia.model.User;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FriendRepositoryTest {

    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        friendRepository.deleteAll();
        userRepository.deleteAll();
    }

    @AfterAll
    public void clearAll() {
        friendRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void whenSaveFriendThenFindById() {
        var userOffer = new User();
        userOffer.setName("testName1");
        userOffer.setLogin("test1@example.com");
        userOffer.setPassword("123t1");
        var userAccept = new User();
        userAccept.setName("testName2");
        userAccept.setLogin("test2@example.com");
        userAccept.setPassword("123t2");
        userRepository.save(userOffer);
        userRepository.save(userAccept);
        var friend = new Friend();
        friend.setUser(userOffer);
        friend.setFriend(userAccept);
        friend.setStatus(true);
        friendRepository.save(friend);
        var foundFriend = friendRepository.findById(friend.getId());
        assertThat(foundFriend).isPresent();
        assertThat(foundFriend.get().getFriend().getName()).isEqualTo("testName2");
    }

    @Test
    public void whenFindAllThenReturnAllFriends() {
        var userOffer = new User();
        userOffer.setName("testName1");
        userOffer.setLogin("test1@example.com");
        userOffer.setPassword("123t1");
        var userAccept1 = new User();
        userAccept1.setName("testName2");
        userAccept1.setLogin("test2@example.com");
        userAccept1.setPassword("123t2");
        var userAccept2 = new User();
        userAccept2.setName("testName3");
        userAccept2.setLogin("test3@example.com");
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
        var friends = friendRepository.findAll();
        assertThat(friends).hasSize(2);
        assertThat(friends).containsExactlyInAnyOrder(friend1, friend2);
    }

    @Test
    public void whenDeleteFriendThenFriendNotFound() {
        var userOffer = new User();
        userOffer.setName("testName1");
        userOffer.setLogin("test1@example.com");
        userOffer.setPassword("123t1");
        var userAccept = new User();
        userAccept.setName("testName2");
        userAccept.setLogin("test2@example.com");
        userAccept.setPassword("123t2");
        userRepository.save(userOffer);
        userRepository.save(userAccept);
        var friend = new Friend();
        friend.setUser(userOffer);
        friend.setFriend(userAccept);
        friend.setStatus(true);
        friendRepository.save(friend);
        friendRepository.delete(friend);
        var foundFriend = friendRepository.findById(friend.getId());
        assertThat(foundFriend).isNotPresent();
        assertThat(friendRepository.findAll()).doesNotContain(friend);
    }
}
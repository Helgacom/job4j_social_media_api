package ru.job4j.socialmedia.repository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.socialmedia.model.User;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
    }

    @AfterAll
    public void clearAll() {
        userRepository.deleteAll();
    }

    @Test
    public void whenSaveUserThenFindById() {
        var user = new User();
        user.setName("testName");
        user.setLogin("test@example.com");
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
        user1.setLogin("test1@example.com");
        user1.setPassword("123t1");
        var user2 = new User();
        user2.setName("testName2");
        user2.setLogin("test2@example.com");
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
        user.setLogin("test@example.com");
        user.setPassword("123t");
        userRepository.save(user);
        userRepository.delete(user);
        var foundUser = userRepository.findById(user.getId());
        assertThat(foundUser).isNotPresent();
        assertThat(userRepository.findAll()).doesNotContain(user);
    }
}
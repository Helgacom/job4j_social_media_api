package ru.job4j.socialmedia.repository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.socialmedia.model.Message;
import ru.job4j.socialmedia.model.User;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MessageRepositoryTest {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        messageRepository.deleteAll();
        userRepository.deleteAll();
    }

    @AfterAll
    public void clearAll() {
        messageRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void whenSaveMessageThenFindById() {
        var userFrom = new User();
        userFrom.setName("testName1");
        userFrom.setLogin("test1@example.com");
        userFrom.setPassword("123t1");
        var userTo = new User();
        userTo.setName("testName2");
        userTo.setLogin("test2@example.com");
        userTo.setPassword("123t2");
        userRepository.save(userFrom);
        userRepository.save(userTo);
        var message = new Message();
        message.setText("test");
        message.setAccept(true);
        message.setUserFrom(userFrom);
        message.setUserTo(userTo);
        messageRepository.save(message);
        var foundMessage = messageRepository.findById(message.getId());
        assertThat(foundMessage).isPresent();
        assertThat(foundMessage.get().getUserFrom().getName()).isEqualTo("testName1");
        assertThat(foundMessage.get().getUserTo().getName()).isEqualTo("testName2");
    }

    @Test
    public void whenFindAllThenReturnAllMessages() {
        var userFrom = new User();
        userFrom.setName("testName1");
        userFrom.setLogin("test1@example.com");
        userFrom.setPassword("123t1");
        var userTo1 = new User();
        userTo1.setName("testName2");
        userTo1.setLogin("test2@example.com");
        userTo1.setPassword("123t2");
        var userTo2 = new User();
        userTo2.setName("testName3");
        userTo2.setLogin("test3@example.com");
        userTo2.setPassword("123t3");
        userRepository.save(userFrom);
        userRepository.save(userTo1);
        userRepository.save(userTo2);
        var message1 = new Message();
        message1.setText("test1");
        message1.setAccept(true);
        message1.setUserFrom(userFrom);
        message1.setUserTo(userTo1);
        var message2 = new Message();
        message2.setText("test2");
        message2.setAccept(true);
        message2.setUserFrom(userFrom);
        message2.setUserTo(userTo2);
        messageRepository.save(message1);
        messageRepository.save(message2);
        var messages = messageRepository.findAll();
        assertThat(messages).hasSize(2);
        assertThat(messages).containsExactlyInAnyOrder(message1, message2);
    }

    @Test
    public void whenDeleteMessageThenMessageNotFound() {
        var userFrom = new User();
        userFrom.setName("testName1");
        userFrom.setLogin("test1@example.com");
        userFrom.setPassword("123t1");
        var userTo = new User();
        userTo.setName("testName2");
        userTo.setLogin("test2@example.com");
        userTo.setPassword("123t2");
        userRepository.save(userFrom);
        userRepository.save(userTo);
        var message = new Message();
        message.setText("test");
        message.setAccept(true);
        message.setUserFrom(userFrom);
        message.setUserTo(userTo);
        messageRepository.save(message);
        messageRepository.delete(message);
        var foundMessage = messageRepository.findById(message.getId());
        assertThat(foundMessage).isNotPresent();
        assertThat(messageRepository.findAll()).doesNotContain(message);
    }
}
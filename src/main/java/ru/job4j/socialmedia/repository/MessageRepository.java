package ru.job4j.socialmedia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.job4j.socialmedia.model.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Modifying
    @Query("delete from Message as message where message.id = :id")
    int delete(@Param("id") Long id);
}

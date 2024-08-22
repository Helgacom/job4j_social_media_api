package ru.job4j.socialmedia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.job4j.socialmedia.model.Friend;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    List<Friend> findFriendByUserId(Long id);

    @Modifying
    @Query("delete from Friend as friend where friend.id = :id")
    int delete(@Param("id") Long id);
}

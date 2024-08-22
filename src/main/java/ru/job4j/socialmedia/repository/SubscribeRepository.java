package ru.job4j.socialmedia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.job4j.socialmedia.model.Subscribe;

import java.util.List;
import java.util.Optional;

public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {

    @Modifying
    @Query("delete from Subscribe as subscribe where subscribe.id = :id")
    int delete(@Param("id") Long id);

    List<Subscribe> findByUserSubscriberId(Long id);

    @Query("""
            select subscribe from Subscribe as subscribe
            where subscribe.userSubscriber.id = :userSubscriberId and subscribe.userTo.id = :userToId
            """)
    Optional<Subscribe> findSubscribeByUserSubscriberAndUserToId(@Param("userSubscriberId") Long userSubscriberId, @Param("userToId") Long userToId);
}

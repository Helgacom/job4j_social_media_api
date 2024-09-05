package ru.job4j.socialmedia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.socialmedia.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Modifying
    @Query("delete from User as user where user.id = :id")
    int delete(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query("""
        update User u
        set u.name = :#{#user.name},
        u.login = :#{#user.login},
        u.password = :#{user.password}
        where u.id = :#{#user.id}
        """)
    int update(@Param("user") User user);

    @Query("""
            select user from User as user
            where user.login = :login and user.password = :password
            """)
    Optional<User> findByLoginAndPassword(@Param("login") String login, @Param("password") String password);

    @Query("""
            select user from User as user
            join Subscribe s on user.id = s.userSubscriber.id
            where s.userTo.id = :id
            """)
    List<User> findAllSubscribersById(@Param("id") Long id);

    @Query("""
            select u from User as u
            join Friend as f on u.id = f.friend.id
            where f.user.id = :id and f.status = true
            """)
    List<User> findAllFriendsById(@Param("id") Long id);

    Optional<User> findByName(String name);

    Boolean existsByName(String name);

    Boolean existsByLogin(String login);
}


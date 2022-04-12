package com.hotabmax.application.repositories;

import com.hotabmax.application.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "select id, login, password from userdata where login = :login",
            nativeQuery = true)
    List<User> findByLogin(String login);

    @Modifying
    @Transactional
    @Query(value = "delete from userdata where login = :login",
            nativeQuery = true)
    void deleteByLogin(String login);
}

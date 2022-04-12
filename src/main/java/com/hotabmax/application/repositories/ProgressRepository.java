package com.hotabmax.application.repositories;

import com.hotabmax.application.models.Progress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
public interface ProgressRepository extends JpaRepository<Progress, Long> {
    @Query(value = "select id, login, attempts, success, middleattempts from progress where login = :login",
            nativeQuery = true)
    List<Progress> findByLogin(String login);

    @Modifying
    @Transactional
    @Query(value = "delete from progress where login = :login",
            nativeQuery = true)
    void deleteByLogin(String login);

    @Modifying
    @Transactional
    @Query(value = "update progress set attempts = attempts + :attempts where login = :login",
            nativeQuery = true)
    void tranzactionAddAttempts(String login, int attempts);

    @Modifying
    @Transactional
    @Query(value = "update progress set success = success + :success where login = :login",
            nativeQuery = true)
    void tranzactionAddSuccess(String login, int success);

    @Modifying
    @Transactional
    @Query(value = "update progress set middleattempts = :middleattempts where login = :login",
            nativeQuery = true)
    void tranzactionChangeMiddleAttempts(String login, float middleattempts);
}

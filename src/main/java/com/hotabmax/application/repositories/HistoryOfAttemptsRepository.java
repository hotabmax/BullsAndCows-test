package com.hotabmax.application.repositories;

import com.hotabmax.application.models.HistoryOfAttempts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
public interface HistoryOfAttemptsRepository extends JpaRepository<HistoryOfAttempts, Long> {
    @Query(value = "select id, login, namber, bulls, cows from historyofattempts where login = :login",
            nativeQuery = true)
    List<HistoryOfAttempts> findByLogin(String login);

    @Modifying
    @Transactional
    @Query(value = "delete from historyofattempts where login = :login",
            nativeQuery = true)
    void deleteByLogin(String login);
}

package com.hotabmax.application.servicesJPA;

import com.hotabmax.application.models.HistoryOfAttempts;
import com.hotabmax.application.repositories.HistoryOfAttemptsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryOfAttemptsService {
    @Autowired
    HistoryOfAttemptsRepository historyOfAttemptsRepository;

    public List<HistoryOfAttempts> findByLogin(String login){
        return historyOfAttemptsRepository.findByLogin(login);
    }

    public void create(HistoryOfAttempts historyOfAttempts){
        historyOfAttemptsRepository.save(historyOfAttempts);
    }

    public void deleteByLogin(String login){
        historyOfAttemptsRepository.deleteByLogin(login);
    }
}

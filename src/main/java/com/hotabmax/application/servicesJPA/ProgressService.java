package com.hotabmax.application.servicesJPA;

import com.hotabmax.application.models.Progress;
import com.hotabmax.application.repositories.ProgressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgressService {
    @Autowired
    ProgressRepository progressRepository;

    public List<Progress> findAll(){
        return progressRepository.findAll();
    }

    public List<Progress> findByLogin(String login){
        return progressRepository.findByLogin(login);
    }

    public void deleteByLogin(String login){
        progressRepository.deleteByLogin(login);
    }

    public void create(Progress progress){
        progressRepository.save(progress);
    }

    public void tranzactionAddAttempts(String login, int attempts){
        progressRepository.tranzactionAddAttempts(login, attempts);
    }

    public void tranzactionAddSuccesses(String login, int success){
        progressRepository.tranzactionAddSuccess(login, success);
    }

    public void tranzactionChangeMiddleAttempts(String login){
        if (progressRepository.findByLogin(login).get(0).getSuccess() != 0){
            progressRepository.tranzactionChangeMiddleAttempts(login,
                    ((float)progressRepository.findByLogin(login).get(0).getAttempts()
                            /
                            (float)progressRepository.findByLogin(login).get(0).getSuccess()));
        }
    }
}

package com.hotabmax.services;

import com.hotabmax.application.models.Progress;
import com.hotabmax.application.servicesJPA.ProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChangerMiddleAttempts {
    @Autowired
    private ProgressService progressService;

    public String setMiddleAttempts(String login){
        progressService.tranzactionChangeMiddleAttempts(login);
        return "Успех";
    }
}

package com.hotabmax.application.services;

import com.hotabmax.application.models.Progress;
import com.hotabmax.application.servicesJPA.ProgressService;
import com.hotabmax.services.ChangerMiddleAttempts;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ChangerMiddleAttemptsTest {
    @Autowired
    private ChangerMiddleAttempts changerMiddleAttempts;
    @Autowired
    private ProgressService progressService;
    @Test
    void setMiddleAttempts() {
        try {
            progressService.create(new Progress("Тест", 1, 2, (float)1));
            changerMiddleAttempts.setMiddleAttempts("Тест");
            assertEquals(0.5, progressService.findByLogin("Тест").get(0).getMiddleattempts());
            progressService.deleteByLogin("Тест");
        } catch (Exception exc){
            progressService.deleteByLogin("Тест");
        }
    }
}
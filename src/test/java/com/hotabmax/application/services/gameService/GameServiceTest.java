package com.hotabmax.application.services.gameService;

import com.hotabmax.application.servicesJPA.HistoryOfAttemptsService;
import com.hotabmax.application.servicesJPA.ProgressService;
import com.hotabmax.services.ChangerMiddleAttempts;
import com.hotabmax.services.gameService.GameService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class GameServiceTest {
    @Autowired
    private GameService gameService;
    @Autowired
    private HistoryOfAttemptsService historyOfAttemptsService;
    @Autowired
    private ProgressService progressService;
    @Autowired
    private ChangerMiddleAttempts changerMiddleAttempts;
    @Test
    void play() {
        try {
            gameService.play("Тест", 1234);
            gameService.play("Тест", gameService.getRandom());
            gameService.play("Тест", 1234);
            changerMiddleAttempts.setMiddleAttempts("Тест");
            assertEquals(3, progressService.findByLogin("Тест").get(0).getMiddleattempts());
            historyOfAttemptsService.deleteByLogin("Тест");
            progressService.deleteByLogin("Тест");
        } catch (Exception exc){
            historyOfAttemptsService.deleteByLogin("Тест");
            progressService.deleteByLogin("Тест");
        }

    }
}
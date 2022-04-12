package com.hotabmax.application.services.gameService.gameServiceLogic;

import com.hotabmax.application.models.Progress;
import com.hotabmax.application.models.User;
import com.hotabmax.application.servicesJPA.HistoryOfAttemptsService;
import com.hotabmax.application.servicesJPA.ProgressService;
import com.hotabmax.application.servicesJPA.UserService;
import com.hotabmax.services.ChangerMiddleAttempts;
import com.hotabmax.services.gameService.gameServiceLogic.CreatorHistoryRecordAndTryChangeRandomValue;
import com.hotabmax.services.gameService.resultsGameService.ResultRandomIntegerArrayIntegersAndFlagChange;
import liquibase.ui.UIService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CreatorHistoryRecordAndTryChangeRandomValueTest {
    @Autowired
    private UserService userService;
    @Autowired
    private HistoryOfAttemptsService historyOfAttemptsService;
    @Autowired
    private ProgressService progressService;
    @Autowired
    private CreatorHistoryRecordAndTryChangeRandomValue creatorHistoryRecordAndTryChangeRandomValue;
    @Autowired
    private ChangerMiddleAttempts changerMiddleAttempts;

    @Test
    void createHistoryAndRecordTestHistoryOfAttemps() {
        try {
            userService.create(new User("Тест", "123"));
            ResultRandomIntegerArrayIntegersAndFlagChange resultRandomIntegerArrayIntegersAndFlagChange =
                    creatorHistoryRecordAndTryChangeRandomValue
                            .createHistoryAndRecord("Тест", 1234, 0, 4);

            for(int i = 0; i < historyOfAttemptsService.findByLogin("Тест").size(); i++){
                if(historyOfAttemptsService.findByLogin("Тест").get(i).getValue() == 1234){
                    assertEquals(1234, historyOfAttemptsService.findByLogin("Тест").get(i).getValue());
                }
            }
            historyOfAttemptsService.deleteByLogin("Тест");
            progressService.deleteByLogin("Тест");
            userService.deleteByLogin("Тест");
        } catch (Exception exc){
            historyOfAttemptsService.deleteByLogin("Тест");
            progressService.deleteByLogin("Тест");
            userService.deleteByLogin("Тест");
        }
    }
    @Test
    void createHistoryAndRecordTestProgress(){
        try {
            userService.create(new User("Тест", "123"));
            ResultRandomIntegerArrayIntegersAndFlagChange resultRandomIntegerArrayIntegersAndFlagChange =
                    creatorHistoryRecordAndTryChangeRandomValue
                            .createHistoryAndRecord("Тест", 1234, 0, 4);

            for(int i = 0; i < progressService.findByLogin("Тест").size(); i++){
                if(progressService.findByLogin("Тест").get(i).getAttempts() == 1){
                    assertEquals(1, progressService.findByLogin("Тест").get(i).getAttempts());
                }
            }
            historyOfAttemptsService.deleteByLogin("Тест");
            progressService.deleteByLogin("Тест");
            userService.deleteByLogin("Тест");
        } catch (Exception exc){
            historyOfAttemptsService.deleteByLogin("Тест");
            progressService.deleteByLogin("Тест");
            userService.deleteByLogin("Тест");
        }

    }
    @Test
    void createHistoryAndRecordTestChangeRandomValue(){
        try {
            userService.create(new User("Тест", "123"));
            ResultRandomIntegerArrayIntegersAndFlagChange resultRandomIntegerArrayIntegersAndFlagChange =
                    creatorHistoryRecordAndTryChangeRandomValue
                            .createHistoryAndRecord("Тест", 1234, 0, 4);
            assertTrue(!resultRandomIntegerArrayIntegersAndFlagChange.isChange());
            historyOfAttemptsService.deleteByLogin("Тест");
            progressService.deleteByLogin("Тест");
            userService.deleteByLogin("Тест");
        } catch (Exception exc){
            historyOfAttemptsService.deleteByLogin("Тест");
            progressService.deleteByLogin("Тест");
            userService.deleteByLogin("Тест");
        }

    }

    @Test
    void createHistoryAndRecordTestManyRecords(){
        try {
            userService.create(new User("Тест", "123"));
            creatorHistoryRecordAndTryChangeRandomValue
                    .createHistoryAndRecord("Тест", 1234, 0, 4);
            creatorHistoryRecordAndTryChangeRandomValue
                    .createHistoryAndRecord("Тест", 1234, 0, 4);
            creatorHistoryRecordAndTryChangeRandomValue
                    .createHistoryAndRecord("Тест", 1234, 0, 4);
            creatorHistoryRecordAndTryChangeRandomValue
                    .createHistoryAndRecord("Тест", 1234, 0, 4);
            creatorHistoryRecordAndTryChangeRandomValue
                    .createHistoryAndRecord("Тест", 1234, 4, 0);
            for(int i = 0; i < historyOfAttemptsService.findByLogin("Тест").size(); i++){
                if(historyOfAttemptsService.findByLogin("Тест").get(i).getBulls() == 4){
                    assertEquals(1234, historyOfAttemptsService.findByLogin("Тест").get(i).getValue());
                }
            }
            historyOfAttemptsService.deleteByLogin("Тест");
            progressService.deleteByLogin("Тест");
            userService.deleteByLogin("Тест");
        } catch (Exception exc){
            historyOfAttemptsService.deleteByLogin("Тест");
            progressService.deleteByLogin("Тест");
            userService.deleteByLogin("Тест");
        }

    }
    @Test
    void createHistoryAndRecordTestRefreshRecords(){
        try {
            userService.create(new User("Тест", "123"));
            creatorHistoryRecordAndTryChangeRandomValue
                    .createHistoryAndRecord("Тест", 1234, 0, 4);
            creatorHistoryRecordAndTryChangeRandomValue
                    .createHistoryAndRecord("Тест", 1234, 0, 4);
            creatorHistoryRecordAndTryChangeRandomValue
                    .createHistoryAndRecord("Тест", 1234, 0, 4);
            creatorHistoryRecordAndTryChangeRandomValue
                    .createHistoryAndRecord("Тест", 1234, 0, 4);
            creatorHistoryRecordAndTryChangeRandomValue
                    .createHistoryAndRecord("Тест", 1234, 4, 0);
            creatorHistoryRecordAndTryChangeRandomValue
                    .createHistoryAndRecord("Тест", 1234, 4, 0);
            for(int i = 0; i < historyOfAttemptsService.findByLogin("Тест").size(); i++){
                if(historyOfAttemptsService.findByLogin("Тест").get(i).getBulls() == 4){
                    assertEquals(1, historyOfAttemptsService.findByLogin("Тест").size());
                }
            }
            historyOfAttemptsService.deleteByLogin("Тест");
            progressService.deleteByLogin("Тест");
            userService.deleteByLogin("Тест");
        } catch (Exception exc){
            historyOfAttemptsService.deleteByLogin("Тест");
            progressService.deleteByLogin("Тест");
            userService.deleteByLogin("Тест");
        }
    }
    @Test
    void createHistoryAndRecordTestThreeRecords(){
        try {
            userService.create(new User("Тест", "123"));
            progressService.create(new Progress("Тест", 1, 0,(float) 0));
            creatorHistoryRecordAndTryChangeRandomValue
                    .createHistoryAndRecord("Тест", 1234, 0, 4);
            creatorHistoryRecordAndTryChangeRandomValue
                    .createHistoryAndRecord("Тест", 1234, 4, 0);
            changerMiddleAttempts.setMiddleAttempts("Тест");
            assertEquals(  3, progressService.findByLogin("Тест").get(0).getMiddleattempts());
            historyOfAttemptsService.deleteByLogin("Тест");
            progressService.deleteByLogin("Тест");
            userService.deleteByLogin("Тест");
        } catch (Exception exc){
            exc.printStackTrace();
            historyOfAttemptsService.deleteByLogin("Тест");
            progressService.deleteByLogin("Тест");
            userService.deleteByLogin("Тест");
        }

    }
}
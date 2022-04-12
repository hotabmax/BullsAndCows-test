package com.hotabmax.services.gameService.gameServiceLogic;

import com.hotabmax.application.models.HistoryOfAttempts;
import com.hotabmax.application.models.Progress;
import com.hotabmax.application.servicesJPA.HistoryOfAttemptsService;
import com.hotabmax.application.servicesJPA.ProgressService;
import com.hotabmax.services.gameService.resultsGameService.ResultRandomIntegerArrayIntegersAndFlagChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CreatorHistoryRecordAndTryChangeRandomValue {
    @Autowired
    private ProgressService progressService;
    @Autowired
    private HistoryOfAttemptsService historyOfAttemptsService;

    ResultRandomIntegerArrayIntegersAndFlagChange randomIntegerAndArrayIntegers;
    public ResultRandomIntegerArrayIntegersAndFlagChange createHistoryAndRecord(String login,
                                                                                int value,
                                                                                int bulls,
                                                                                int cows){
        int random = 0;
        int[] randomNumbers = new int[4];
        if(progressService.findByLogin(login).size() != 0) {
            if (bulls == 4) {
                List<HistoryOfAttempts> historyOfAttempts = historyOfAttemptsService.findByLogin(login);
                for (HistoryOfAttempts h : historyOfAttempts){
                    if(h.getBulls() == 4) historyOfAttemptsService.deleteByLogin(login);
                }
                progressService.tranzactionAddSuccesses(login, 1);
                progressService.tranzactionAddAttempts(login, 1);

                randomIntegerAndArrayIntegers = GeneratorRandom.random();
                random = randomIntegerAndArrayIntegers.getRandom();
                randomNumbers = randomIntegerAndArrayIntegers.getRandomNumbers();

                historyOfAttemptsService.create(new HistoryOfAttempts(login, value, bulls, cows));

                return new ResultRandomIntegerArrayIntegersAndFlagChange(random, randomNumbers, true);

            } else{
                progressService.tranzactionAddAttempts(login, 1);
                historyOfAttemptsService.create(new HistoryOfAttempts(login, value, bulls, cows));
                return new ResultRandomIntegerArrayIntegersAndFlagChange(random, randomNumbers, false);
            }
        } else {
            if (bulls == 4) {
                List<HistoryOfAttempts> historyOfAttempts = historyOfAttemptsService.findByLogin(login);
                for (HistoryOfAttempts h : historyOfAttempts){
                    if(h.getBulls() == 4) historyOfAttemptsService.deleteByLogin(login);
                }
                randomIntegerAndArrayIntegers = GeneratorRandom.random();
                random = randomIntegerAndArrayIntegers.getRandom();
                randomNumbers = randomIntegerAndArrayIntegers.getRandomNumbers();

                progressService.create(new Progress(login, 1, 1,(float) 1));

                historyOfAttemptsService.create(new HistoryOfAttempts(login, value, bulls, cows));

                return new ResultRandomIntegerArrayIntegersAndFlagChange(random, randomNumbers, true);

            } else{
                progressService.create(new Progress(login, 1, 0,(float) 0));
                historyOfAttemptsService.create(new HistoryOfAttempts(login, value, bulls, cows));
                return new ResultRandomIntegerArrayIntegersAndFlagChange(random, randomNumbers, false);
            }
        }
    }
}

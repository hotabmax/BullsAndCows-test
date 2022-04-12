package com.hotabmax.services.gameService;

import com.hotabmax.application.interfacesServices.GameServiceInterface;
import com.hotabmax.application.models.Progress;
import com.hotabmax.application.servicesJPA.HistoryOfAttemptsService;
import com.hotabmax.application.servicesJPA.ProgressService;
import com.hotabmax.services.gameService.gameServiceLogic.*;
import com.hotabmax.services.gameService.resultsGameService.ResultBullsAndCows;
import com.hotabmax.services.gameService.resultsGameService.ResultRandomIntegerArrayIntegersAndFlagChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * GameService consist of GeneratorRandom, ComparatorRandomAndInputValue and
 * CreatorHistoryRecordAndTryChangeRandomValue. After create GameService
 * example constructor call method GeneratorRandom().random() and set variables random and
 * randomNumbers.
 */
@Service
public class GameService implements GameServiceInterface {
    @Autowired
    private CreatorHistoryRecordAndTryChangeRandomValue creatorHistoryRecordAndTryChangeRandomValue;

    private static int random;
    private static int[] randomNumbers;

    public GameService() {
        ResultRandomIntegerArrayIntegersAndFlagChange resultRandomIntegerArrayIntegersAndFlagChange;
        resultRandomIntegerArrayIntegersAndFlagChange = GeneratorRandom.random();
        random = resultRandomIntegerArrayIntegersAndFlagChange.getRandom();
        randomNumbers = resultRandomIntegerArrayIntegersAndFlagChange.getRandomNumbers();
        System.out.println(random);
    }

    @Override
    public String play(String login, int inputValue){

        ResultRandomIntegerArrayIntegersAndFlagChange resultRandomIntegerArrayIntegersAndFlagChange;

        ResultBullsAndCows resultBullsAndCows = ComparatorRandomAndInputValue.compare(inputValue, randomNumbers);

        resultRandomIntegerArrayIntegersAndFlagChange = creatorHistoryRecordAndTryChangeRandomValue.createHistoryAndRecord(login, inputValue,
                        resultBullsAndCows.getBulls(),
                        resultBullsAndCows.getCows());

        if (resultRandomIntegerArrayIntegersAndFlagChange.isChange()){
            random = resultRandomIntegerArrayIntegersAndFlagChange.getRandom();
            randomNumbers = resultRandomIntegerArrayIntegersAndFlagChange.getRandomNumbers();
        }
        System.out.println(random);
        return "Успех";
    }

    public static int getRandom() {
        return random;
    }
}

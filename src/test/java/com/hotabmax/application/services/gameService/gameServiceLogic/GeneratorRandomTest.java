package com.hotabmax.application.services.gameService.gameServiceLogic;

import com.hotabmax.services.gameService.gameServiceLogic.GeneratorRandom;
import com.hotabmax.services.gameService.resultsGameService.ResultRandomIntegerArrayIntegersAndFlagChange;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GeneratorRandomTest {

    private GeneratorRandom generatorRandom = new GeneratorRandom();
    private int[] randomNumbers = new int[4];
    private boolean result;
    @Test
    void random() {
        ResultRandomIntegerArrayIntegersAndFlagChange resultRandomIntegerArrayIntegersAndFlagChange
                = generatorRandom.random();
        randomNumbers = resultRandomIntegerArrayIntegersAndFlagChange.getRandomNumbers();
        if (randomNumbers[0] != randomNumbers[1] &&
            randomNumbers[0] != randomNumbers[2] &&
            randomNumbers[0] != randomNumbers[3] &&
            randomNumbers[1] != randomNumbers[0] &&
            randomNumbers[1] != randomNumbers[2] &&
            randomNumbers[1] != randomNumbers[3] &&
            randomNumbers[2] != randomNumbers[0] &&
            randomNumbers[2] != randomNumbers[1] &&
            randomNumbers[2] != randomNumbers[3] &&
            randomNumbers[3] != randomNumbers[0] &&
            randomNumbers[3] != randomNumbers[1] &&
            randomNumbers[3] != randomNumbers[2]) result = true;
            assertTrue(result);
    }
}
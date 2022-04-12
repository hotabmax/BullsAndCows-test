package com.hotabmax.application.services.gameService.gameServiceLogic;

import com.hotabmax.services.gameService.gameServiceLogic.ComparatorRandomAndInputValue;
import com.hotabmax.services.gameService.resultsGameService.ResultBullsAndCows;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ComparatorRandomAndInputValueTest {
    private ComparatorRandomAndInputValue comparatorRandomAndInputValue =
            new ComparatorRandomAndInputValue();
    private ResultBullsAndCows resultBullsAndCows;
    private int[] randomNumbers = new int[4];
    @Test
    void compareTest4Bulls() {
        randomNumbers[0] = 1;
        randomNumbers[1] = 2;
        randomNumbers[2] = 3;
        randomNumbers[3] = 4;
        resultBullsAndCows = comparatorRandomAndInputValue.compare(1234, randomNumbers);
        assertEquals(4, resultBullsAndCows.getBulls());
    }

    @Test
    void compareTest0Cows(){
        randomNumbers[0] = 1;
        randomNumbers[1] = 2;
        randomNumbers[2] = 3;
        randomNumbers[3] = 4;
        resultBullsAndCows = comparatorRandomAndInputValue.compare(1234, randomNumbers);
        assertEquals(0, resultBullsAndCows.getCows());
    }

    @Test
    void compareTest3Bulls(){
        randomNumbers[0] = 1;
        randomNumbers[1] = 2;
        randomNumbers[2] = 3;
        randomNumbers[3] = 5;
        resultBullsAndCows = comparatorRandomAndInputValue.compare(1234, randomNumbers);
        assertEquals(3, resultBullsAndCows.getBulls());
    }

    @Test
    void compareTest4Cows(){
        randomNumbers[0] = 2;
        randomNumbers[1] = 1;
        randomNumbers[2] = 4;
        randomNumbers[3] = 3;
        resultBullsAndCows = comparatorRandomAndInputValue.compare(1234, randomNumbers);
        assertEquals(4, resultBullsAndCows.getCows());
    }
}
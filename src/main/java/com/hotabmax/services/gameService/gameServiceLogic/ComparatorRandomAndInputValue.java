package com.hotabmax.services.gameService.gameServiceLogic;

import com.hotabmax.services.gameService.resultsGameService.ResultBullsAndCows;

public class ComparatorRandomAndInputValue {

    public static ResultBullsAndCows compare(int inputValue, int[] randomNumbers){
        int[] valueNumbers = new int[4];
        int bulls = 0;
        int cows = 0;

        valueNumbers[0] = inputValue / 1000;
        valueNumbers[1] = inputValue % 1000 / 100;
        valueNumbers[2] = inputValue % 100 / 10;
        valueNumbers[3] = inputValue % 10;

        for(int i = 0; i < randomNumbers.length; i++){
            for(int b = 0; b < valueNumbers.length; b++){
                if(randomNumbers[i] == valueNumbers[b] && i == b) bulls++;
                if(i != b && randomNumbers[i] == valueNumbers[b]) cows++;
            }
        }
        return new ResultBullsAndCows(bulls, cows);
    }
}

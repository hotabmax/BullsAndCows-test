package com.hotabmax.services.gameService.gameServiceLogic;


import com.hotabmax.services.gameService.resultsGameService.ResultRandomIntegerArrayIntegersAndFlagChange;

public class GeneratorRandom {

    public static ResultRandomIntegerArrayIntegersAndFlagChange random(){
        int random;
        int[] randomNumbers = new int[4];
        random = (int) (Math.random() * 10000 - 1);
        randomNumbers[0] = random / 1000;
        randomNumbers[1] = random % 1000 / 100;
        randomNumbers[2] = random % 100 / 10;
        randomNumbers[3] = random % 10;
        while (
            randomNumbers[0] == randomNumbers[1] ||            // while at list number = another
            randomNumbers[0] == randomNumbers[2] ||            // generate random
            randomNumbers[0] == randomNumbers[3] ||
            randomNumbers[1] == randomNumbers[0] ||
            randomNumbers[1] == randomNumbers[2] ||
            randomNumbers[1] == randomNumbers[3] ||
            randomNumbers[2] == randomNumbers[0] ||
            randomNumbers[2] == randomNumbers[1] ||
            randomNumbers[2] == randomNumbers[3] ||
            randomNumbers[3] == randomNumbers[0] ||
            randomNumbers[3] == randomNumbers[1] ||
            randomNumbers[3] == randomNumbers[2]
        ){
            random = (int) (Math.random() * 10000 - 1);
            randomNumbers[0] = random / 1000;
            randomNumbers[1] = random % 1000 / 100;
            randomNumbers[2] = random % 100 / 10;
            randomNumbers[3] = random % 10;
        }
        return new ResultRandomIntegerArrayIntegersAndFlagChange(random, randomNumbers, false);
    }
}

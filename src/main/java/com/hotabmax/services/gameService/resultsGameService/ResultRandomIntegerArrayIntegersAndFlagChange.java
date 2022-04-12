package com.hotabmax.services.gameService.resultsGameService;

public class ResultRandomIntegerArrayIntegersAndFlagChange {
    int random;
    int[] randomNumbers;
    boolean change = false;

    public ResultRandomIntegerArrayIntegersAndFlagChange() {
    }

    public ResultRandomIntegerArrayIntegersAndFlagChange(int random, int[] randomNumbers, boolean change) {
        this.random = random;
        this.randomNumbers = randomNumbers;
        this.change = change;
    }

    public int getRandom() {
        return random;
    }

    public int[] getRandomNumbers() {
        return randomNumbers;
    }

    public boolean isChange() {
        return change;
    }
}

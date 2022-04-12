package com.hotabmax.services.gameService.resultsGameService;

public class ResultBullsAndCows {
    private int bulls;
    private int cows;

    public ResultBullsAndCows(int bulls, int cows) {
        this.bulls = bulls;
        this.cows = cows;
    }

    public int getBulls() {
        return bulls;
    }

    public int getCows() {
        return cows;
    }

}

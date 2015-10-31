package com.blackjack.objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
public class Result {

    private Player player;
    private Dealer dealer;
    private IPlayer winner;
    private String winnerName;
    private boolean gameFinished = false;
    private boolean isBlackJack = false;
    private double bet;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
        this.bet = player.getBet();
    }

    public Dealer getDealer() {
        return dealer;
    }

    @Autowired
    public void setDealer(Dealer dealer) {
        this.dealer = dealer;
    }

    public IPlayer getWinner() {
        return winner;
    }

    public void setWinner(IPlayer winner) {
        this.winner = winner;
        this.winnerName = winner.getName();
    }

    public boolean isGameFinished() {
        return gameFinished;
    }

    public void setGameFinished(boolean gameFinished) {
        this.gameFinished = gameFinished;
    }

    public boolean isBlackJack() {
        return isBlackJack;
    }

    public void setIsBlackJack(boolean isBlackJack) {
        this.isBlackJack = isBlackJack;
    }

    public double getBet() {
        return bet;
    }

    public String getWinnerName() {
        return winnerName;
    }

}

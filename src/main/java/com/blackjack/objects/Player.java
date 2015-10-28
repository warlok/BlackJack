package com.blackjack.objects;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dean on 10/26/15.
 */
public class Player implements IPlayer {

    private int playerId;
    private double money;
    private List<Card> cards;
    private int score;
    private int aceAmount;

    public Player() {
        this.cards = new ArrayList<>();
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public void cashIn(double amount) {
        this.money += amount;
    }

    public void takeCard(Card card) {
        cards.add(card);
    }

    public List<Card> getCards() {
        return cards;
    }

    @Override
    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    @Override
    public void setAceAmount(int aceAmount) {
        this.aceAmount = aceAmount;
    }

    @Override
    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void updateScore(int points) {
        this.score += points;
    }

    public void addAce() {
        this.aceAmount++;
    }

    public void checkAce() {
        this.aceAmount--;
    }

    public boolean hasAce() {
        if (aceAmount > 0) {
            return true;
        }
        return false;
    }

}

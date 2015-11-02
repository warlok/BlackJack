package com.blackjack.objects;

import java.util.ArrayList;
import java.util.List;

public class Player implements IPlayer {

    private int playerId;
    private double money;
    private List<Card> cards;
    private int score;
    private transient int aceAmount;
    private double bet;
    private double lastBet;
    private boolean stand;
    private String name = "Player";

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

    public void updateMoney(double money) {
        this.money += money;
    }

    public void cashIn(double amount) {
        this.money += amount;
    }

    public double getBet() {
        return bet;
    }

    public double getLastBet() {
        return lastBet;
    }

    public void setLastBet(double lastBet) {
        this.lastBet = lastBet;
    }

    public void setBet(double bet) {
        this.bet = bet;
    }

    @Override
    public void takeCard(Card card) {
        if (card.getValue().equals("A")) {
            addAce();
        }
        cards.add(card);
        updateScore(card.getPoints());
    }

    @Override
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

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public void updateScore(int points) {
        this.score += points;
    }

    @Override
    public void addAce() {
        this.aceAmount++;
    }

    @Override
    public void checkAce() {
        this.aceAmount--;
    }

    @Override
    public boolean hasAce() {
        if (aceAmount > 0) {
            return true;
        }
        return false;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isStand() {
        return stand;
    }

    public void setStand(boolean stand) {
        this.stand = stand;
    }
}

package com.blackjack.objects;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dean on 10/26/15.
 */
public class Player {

    private int playerId;
    private int money;
    private List<Card> cards;

    public Player() {
        this.cards = new ArrayList<>();
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void takeCard(Card card) {
        cards.add(card);
    }

    public List<Card> getCards() {
        return cards;
    }
}

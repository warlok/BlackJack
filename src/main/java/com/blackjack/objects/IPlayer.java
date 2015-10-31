package com.blackjack.objects;

import java.util.List;

public interface IPlayer {

    void takeCard(Card card);
    List<Card> getCards();
    void setCards(List<Card> cards);
    void setAceAmount(int aceAmount);
    void setScore(int score);
    int getScore();
    void updateScore(int points);
    void addAce();
    void checkAce();
    boolean hasAce();
    public String getName();
}

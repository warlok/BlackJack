package com.blackjack.objects;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

@Component
public class Dealer implements IPlayer {

    private final String[] NOMINALS = new String[]{
            "2","3","4","5","6","7","8","9","10","J","Q","K","A"};
    private List<Card> unsortedDeck;
    private Stack<Card> deck;
    private List<Card> cards = new ArrayList<>();
    private int score;
    private int hiddenScore;
    private int aceAmount;

    public Dealer() {
        this.unsortedDeck = unsortedDeck();
        this.deck = shuffleDeck();
    }

    private ArrayList<Card> unsortedDeck() {
        ArrayList<Card> deck = new ArrayList<>();
        for (Suit suit : Suit.values()) {
            for (String nominal : NOMINALS) {
                Card card = new Card();
                card.setSuit(suit);
                card.setValue(nominal);
                setCardPoints(card);
                deck.add(card);
            }
        }
        return deck;
    }

    private void setCardPoints(Card card) {
        if (card.getValue().equals("A")) {
            card.setPoints(11);
        } else if (card.getValue().matches("[JQK]")) {
            card.setPoints(10);
        } else {
            card.setPoints(Integer.parseInt(card.getValue()));
        }
    }

    public Stack<Card> shuffleDeck() {
        Random rand = new Random();
        ArrayList<Card> unsortedDeck = new ArrayList<>(this.unsortedDeck);
        Stack<Card> deck = new Stack<>();
        int i;
        for (i=0; i < 52; i++) {
            int index = rand.nextInt(unsortedDeck.size());
            Card card = unsortedDeck.get(index);
            unsortedDeck.remove(index);
            deck.add(card);
        }
        return deck;
    }

    public Card getCard() {
        Card card = deck.pop();
        if (deck.size() < 10) {
            deck = shuffleDeck();
        }
        return card;
    }

    @Override
    public void takeCard(Card card) {
            cards.add(card);
    }

    @Override
    public List<Card> getCards() {
        return cards;
    }

    @Override
    public void updateScore(int points) {
        this.score += points;
    }

    @Override
    public int getScore() {
        return score;
    }

    public void updateHiddenScore(int points) {
        this.hiddenScore += points;
    }

    public int getHiddenScore() {
        return hiddenScore;
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
}

package com.blackjack.objects;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

/**
 * Created by dean on 10/26/15.
 */
@Component
public class Dealer {

    private final String[] NOMINALS = new String[]{
            "2","3","4","5","6","7","8","9","10","J","Q","K","A"};
    private List<Card> unsortedDeck;
    private Stack<Card> deck;
    private List<Card> cards = new ArrayList<>(2);

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
                deck.add(card);
            }
        }
        return deck;
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
        return card;
    }

    public void takeCard(Card card) {
            cards.add(card);
    }

    public List<Card> getCards() {
        return cards;
    }
}

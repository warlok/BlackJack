package com.blackjack;

import com.blackjack.objects.Card;
import com.blackjack.objects.Dealer;

/**
 * Created by dean on 10/27/15.
 */
public class Test {

    public static void main(String[] args) {
        Dealer d = new Dealer();
        int i;
        for (i=0; i<52; i++) {
            Card card = d.getCard();
            System.out.println("Suit = " + card.getSuit() + "; " + card.getValue() + ";");
        }
    }

}

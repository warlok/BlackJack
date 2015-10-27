package com.blackjack.objects;

import java.awt.*;

/**
 * Created by dean on 10/26/15.
 */
public class Card {

    private Suit suit;
    private String value;
    private Image image = null;
    private boolean isHiden = false;

    public Suit getSuit() {
        return suit;
    }

    public void setSuit(Suit suit) {
        this.suit = suit;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public boolean isHiden() {
        return isHiden;
    }

    public void setHiden(boolean isHiden) {
        this.isHiden = isHiden;
    }

}

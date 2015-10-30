package com.blackjack.objects;

import java.awt.*;

public class Card {

    private Suit suit;
    private String value;
    private Image image = null;
    private boolean isHidden = false;
    private int points;

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

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
        return isHidden;
    }

    public void setHiden(boolean isHiden) {
        this.isHidden = isHiden;
    }

}

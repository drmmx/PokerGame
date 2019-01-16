package com.drmmx.devmax.pokergame.game;

public class Card {

    private String suit;
    public String value;

    Card(String cardValue, String cardSuit) {
        suit = cardSuit;
        value = cardValue;
    }

    String getSuit() {
        return suit;
    }

    public String getValue() {
        return value;
    }

    String print() {
        String output;
        output = value + suit;
        return output;
    }
}

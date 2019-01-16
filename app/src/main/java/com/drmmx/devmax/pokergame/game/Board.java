package com.drmmx.devmax.pokergame.game;

public class Board {

    private int pot;
    private int maxBet;
    public CardSet river = new CardSet();

    Board() {
        pot = 0;
        maxBet = 0;
    }

    void addToPot(int x) {
        pot += x;
    }

    void removeFromPot(int x) {
        pot -= x;
    }

    void setMaxBet(int x) {
        maxBet = x;
    }

    void addToRiver(Card x) {
        river.add(x);
    }

    int getPot() {
        return pot;
    }

    int getMaxBet() {
        return maxBet;
    }

    public CardSet getRiver() {
        return river;
    }

    void clearPot() {
        pot = 0;
    }

    void clearMaxBet() {
        maxBet = 0;
    }

    void clearRiver() {
        river.clear();
    }
}

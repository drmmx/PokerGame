package com.drmmx.devmax.pokergame.game;

import java.util.ArrayList;

public class Player implements Comparable<Player> {

    public String name;
    private int money;
    public CardSet hand = new CardSet();
    private int amountBet;
    private String state;
    private ArrayList<Integer> handResults = new ArrayList();

    Player(String playerName, int playerMoney) {
        name = playerName;
        money = playerMoney;
        amountBet = 0;
        state = "active|nb";
    }

    void editMoney(int x) {
        money = money + x;
    }

    void deal(Card x) {
        hand.add(x);
    }

    void clearHand() {
        hand.clear();
    }

    void setState(String x) {
        state = x;
    }

    void setHandResults(ArrayList<Integer> x) {
        handResults = x;
    }

    void setAmountBet(int x) {
        amountBet = x;
    }

    public int bet(int amount) {
        money -= amount;
        amountBet += amount;
        return amount;
    }

    public CardSet getHand() {
        return hand;
    }

    ArrayList<Integer> getHandResults() {
        return handResults;
    }

    String printName() {
        return name;
    }

    String printState() {
        return state;
    }

    void clearAmountBet() {
        amountBet = 0;
    }

    void clearHandResults() {
        handResults.clear();
    }

    int getAmountBet() {
        return amountBet;
    }

    int printMoney() {
        return money;
    }

    @Override public int compareTo(Player compareTo) {
        int compareValue = compareTo.getAmountBet();
        return this.amountBet - compareValue;
    }
}
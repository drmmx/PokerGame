package com.drmmx.devmax.pokergame.game;

import java.util.ArrayList;
import java.util.Collections;

public class CardSet {

    private ArrayList<Card> cards = new ArrayList();

    CardSet() {}

    public void add(Card x) {
        cards.add(x);
    }

    Card getFirst() {
        return cards.get(0);
    }

    void remove(Card x) {
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).getSuit().equals(x.getSuit()) && cards.get(i).getValue().equals(x.getValue())) {
                cards.remove(i);
            }
        }
    }

    public int size() {
        return cards.size();
    }

    void shuffle() {
        Collections.shuffle(cards);
    }

    void clear() {
        cards.clear();
    }

    String print() {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < cards.size(); i++) {
            output.append(cards.get(i).print());
            output.append(" ");
        }
        return output.toString();
    }

    CardSet copy() {
        CardSet output = new CardSet();
        for(int i = 0; i < cards.size(); i++)
            output.add(cards.get(i));
        return output;
    }
}

package cards;

import engine.Table;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> deck = new ArrayList<>();

    public Deck() {
        char[] ranks = {'2','3','4','5','6','7','8','9', 'T','J','Q','K','A'};
        char[] suits = {'♠','❤','♣','♦'};

        for (int i = 0; i < Table.DECKS_USED; i++) {
            for (int j = 0; j < ranks.length; j++) {
                for (int k = 0; k < suits.length; k++) {
                    deck.add(new Card(suits[k], ranks[j]));
                }
            }
        }
    }

    public void shuffle(){
        Collections.shuffle(deck);
    }

    public List<Card> getDeck() {
        return deck;
    }
}

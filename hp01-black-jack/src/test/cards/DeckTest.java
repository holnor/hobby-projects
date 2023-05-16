package cards;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {
    private Deck deck;

    @BeforeEach
    void init(){
        deck = new Deck();
    }

    @Test
    void test_deck_size52multiple(){
        assertTrue(deck.getDeck().size() % 52 == 0);
    }

    @Test
    void test_shuffle_loop10RandomOrder(){
        for (int i = 0; i < 10; i++) {
        deck.shuffle();
        assertFalse(deck.getDeck().get(0).getValue() == 2
                && deck.getDeck().get(0).getSuit() == '♠');
        }
    }
}
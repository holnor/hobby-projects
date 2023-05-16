package cards;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    private Card card;

    @Test
    void test_cardT_correctValue(){
        assertEquals(10, new Card('♠', 'T').getValue());
    }

    @Test
    void test_cardJ_correctValue(){
        assertEquals(10, new Card('♠', 'J').getValue());
    }

    @Test
    void test_cardQ_correctValue(){
        assertEquals(10, new Card('♠', 'Q').getValue());
    }

    @Test
    void test_cardK_correctValue(){
        assertEquals(10, new Card('♠', 'K').getValue());
    }

    @Test
    void test_cardA_correctValue(){
        assertEquals(11, new Card('♠', 'A').getValue());
    }

    @Test
    void test_cardNumber_correctValue(){
        assertEquals(5, new Card('♠', '5').getValue());
    }

}
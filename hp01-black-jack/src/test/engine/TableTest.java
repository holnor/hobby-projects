package engine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import participants.Dealer;

import static org.junit.jupiter.api.Assertions.*;

class TableTest {
    private Table table;

    @BeforeEach
    void setUp() {
        table = new Table(2);
    }

    @Test
    void test_addPlayers_playersAdded() {
        table.addPlayers();
        assertEquals(2, table.getPlayers().size());
    }

    @Test
    void test_init_dealerAdded() {
        table.init();
        assertTrue(table.getDealer() instanceof Dealer);
    }

    @Test
    void test_init_randomCradsRemoved() {
        table.init();
        int result = table.getDealer().getDeck().getDeck().size();
        System.out.println(result);
        assertTrue(result < 292 && result > 278);
    }

    @Test
    void test_firstHand_AllParticipantsReceiveTwoCards() {
        table.addPlayers();
        table.firstHand();
        int result = table.getDealer().getHand().size() +
                table.getPlayers().get(0).getHand().size() +
                table.getPlayers().get(1).getHand().size();
        assertEquals(6, result);
    }
}
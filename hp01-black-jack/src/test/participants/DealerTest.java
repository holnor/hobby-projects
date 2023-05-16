package participants;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DealerTest {
    private List<Participant> players;
    private Participant aladar = new Player("Aladár", null);
    private Participant bela = new Player("Béla", null);
    private Dealer dealer;

    @BeforeEach
    void init(){
        dealer = new Dealer(null);
        players = new ArrayList<>();
        players.add(aladar);
        players.add(bela);
    }

    @Test
    void test_deal_oneCardToOnePlayer() {
        dealer.deal(aladar);
        assertEquals(1,aladar.hand.size());
    }

    @Test
    void test_deal_oneCardToAllPlayers() {
        dealer.deal(players);
        assertEquals(2,aladar.hand.size() + bela.hand.size());
    }

}
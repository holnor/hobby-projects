package participants;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DealerTest {
    private List<Player> players;
    private Player aladar = new Player("Aladár",100, null);
    private Player bela = new Player("Béla",100, null);
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
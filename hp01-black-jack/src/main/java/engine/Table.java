package engine;

import participants.Dealer;
import participants.Participant;
import participants.Player;

import java.util.ArrayList;
import java.util.List;

public class Table {
    public static final int ONE_DECK_SIZE = 52;
    public static final int DECKS_USED = 6;
    public static final int CARDS_REMOVE_BOUND_LOW = 20;
    public static final int CARDS_REMOVE_BOUND_HIGH = 35;
    public static final int BLACK_JACK = 21;
    public static final int DEALER_COINS = 1_000_000;

    private Dealer dealer;
    private List<Participant> players;
    private int numberOfPlayers;

    public Table(int numberOfPlayers) {
        this.dealer = new Dealer(this);
        this.numberOfPlayers = numberOfPlayers;
        this.players = new ArrayList<>();
    }

    public void addPlayers(){
        for (int i = 0; i < numberOfPlayers; i++) {
            players.add(new Player("unknown", this));
        }
    }
}

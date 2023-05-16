package engine;

import cards.Card;
import participants.Dealer;
import participants.Participant;
import participants.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Table {
    public static final int ONE_DECK_SIZE = 52;
    public static final int DECKS_USED = 6;
    public static final int CARDS_REMOVE_BOUND_LOW = 20;
    public static final int CARDS_REMOVE_BOUND_HIGH = 35;
    public static final int FIRST_HAND_SIZE = 2;
    public static final int BLACK_JACK = 21;
    public static final int DEALER_COINS = 1_000_000;

    private Dealer dealer;
    private List<Participant> players;
    private int numberOfPlayers;
    private List<Card> deck;
    private List<Card> pile;
    private UserInterface ui = new UserInterface();

    public Table(int numberOfPlayers) {
        this.dealer = new Dealer(this);
        this.numberOfPlayers = numberOfPlayers;
        this.players = new ArrayList<>();
    }

    public void addPlayers() {
        for (int i = 0; i < numberOfPlayers; i++) {
            players.add(new Player("unknown", this));
        }
    }

    public void init() {
        pile = dealer.getPile();
        deck = dealer.getDeck().getDeck();
        dealer.getDeck().shuffle();
        Random random = new Random();
        int cardsToRemove = random
                .nextInt(CARDS_REMOVE_BOUND_HIGH - CARDS_REMOVE_BOUND_LOW + 1) + CARDS_REMOVE_BOUND_LOW;

        for (int i = 0; i < cardsToRemove; i++) {
            pile.add(deck.get(0));
            deck.remove(0);
        }
    }

    public void firstHand() {
        for (int i = 0; i < FIRST_HAND_SIZE; i++) {
            dealer.deal(dealer);
            dealer.deal(players);
        }


    }


    //FOR TESTING


    public List<Participant> getPlayers() {
        return players;
    }

    public Dealer getDealer() {
        return dealer;
    }
}

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
    public static final int MIN_BET = 5;
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
            System.out.println("Enter name of p" + (i + 1) + ":");
            String name = ui.getNames();
            players.add(new Player(name, this));
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
        dealer.deal(dealer);
        for (int i = 0; i < FIRST_HAND_SIZE; i++) {
            dealer.deal(players);
        }
        players.get(0).setActive(true);

        for (Participant player : players) {
            player.placeBet(Table.MIN_BET);
        }

        System.out.println(dealer);
        dealer.deal(dealer);
        displayStats();

        for (Participant player : players) {
            if (checkHandValue(player).equals("BLACK JACK!")) {
                check21(player);
            }
        }
    }

    public void checkBusted(Participant player) {
        if (checkHandValue(player).equals("BUSTED!")) {
            System.out.println(player.getName() + " gets busted! Lost bet: " + player.getBet() + " coins");
            player.looseBet();
            System.out.println(player);
        }
    }

    public void check21(Participant player) {
        if (checkHandValue(player).equals("BLACK JACK!")) {
            System.out.println(dealer);
            if (!checkHandValue(dealer).equals("BLACK JACK!")) {
                System.out.println(player.getName() + " wins! Prize: " + player.getBet() * 2 + " coins");
                player.winBet();
                System.out.println(player);
            } else {
                player.takeBackBet();
                System.out.println("DRAW! " + player.getName() + " bet is returned");
            }
        }
    }

    public String checkHandValue(Participant player) {
        int handValue = player.getHandValue();

        if (handValue > Table.BLACK_JACK) {
            return "BUSTED!";
        } else if (handValue == Table.BLACK_JACK) {
            return "BLACK JACK!";
        } else {
            return "";
        }
    }


    public void displayStats() {
        for (Participant player : players) {
            System.out.println(player);
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

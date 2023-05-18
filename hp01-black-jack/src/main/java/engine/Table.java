package engine;

import cards.Card;
import participants.Dealer;
import participants.Player;
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
    private Player splitHand;
    private List<Player> players;
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
            players.add(new Player(name, 500, this));
        }
    }

    public void init() {
        pile = dealer.getPile();
        deck = dealer.getDeck().getDeck();
        dealer.getDeck().shuffle();
        Random random = new Random();
        int cardsToRemove = random.nextInt(CARDS_REMOVE_BOUND_HIGH - CARDS_REMOVE_BOUND_LOW + 1) + CARDS_REMOVE_BOUND_LOW;

        for (int i = 0; i < cardsToRemove; i++) {
            pile.add(deck.get(0));
            deck.remove(0);
        }
    }

    public void firstHand() {
        for (Player player : players) {
            player.placeBet(Table.MIN_BET);
        }
        dealer.deal(dealer);
        System.out.println(dealer);
        if (dealer.getHand().get(0).getRank() == 'A') {
            for (Player player : players) {
                System.out.println(player.getName() + "! Dealer has an Ace. Would you like to make insurance (y/n):");
                if (ui.askInsurance() == 'y') {
                    player.insure();
                    System.out.println("Insured!");
                }
            }
        }


        for (int i = 0; i < FIRST_HAND_SIZE; i++) {
            dealer.deal(players);
            for (Player player : players) {
                Card lastCard = player.getHand().get(player.getHand().size() - 1);
                if (lastCard.getRank() == 'A' && player.getHandValue() != BLACK_JACK) {
                    System.out.println(player);
                    System.out.println("Set Ace value (1 OR 11):");
                    lastCard.setValue(ui.getAceValue());
                }
            }
        }
        players.get(0).setActive(true);
        displayStats();

        dealer.deal(dealer);


        for (Player player : players) {
            if (dealer.getHandValue() == BLACK_JACK) {
                System.out.println("DEALER HAS BLACK JACK!");
                if (player.getHandValue() == BLACK_JACK) {
                    System.out.println("******* TIE! ********");
                    System.out.println(player);
                    player.takeBackBet();
                }
            } else {
                if (player.getHandValue() == BLACK_JACK) {
                    System.out.println("******* BLACK JACK! ********");
                }
                System.out.println(player);
            }
        }
    }

    public void nextPlayer() {
        if (players.get(players.size() - 1).isActive()) {
            players.get(players.size() - 1).setActive(false);

        } else {
            for (int i = 0; i < players.size() - 1; i++) {
                if (players.get(i).isActive()) {
                    players.get(i).setActive(false);
                    players.get(i + 1).setActive(true);
                    break;
                }
            }
        }
    }

    public String checkHit(Player player) {
        Card lastCard = player.getHand().get(player.getHand().size() - 1);
        player.hit();
        if (lastCard.getRank() == 'A') {
            System.out.println("Set Ace value (1 OR 11):");
            lastCard.setValue(ui.getAceValue());
        }
        String handValue = checkHandValue(player);
        if (handValue.equals("BUSTED!") || handValue.equals("BLACK JACK!")) {
            System.out.println("******* " + handValue + " ********");
        }

        if (handValue.equals("BUSTED!")) {
            player.looseBet();
        }
        return handValue;
    }

    public void startTurn() {
        System.out.println("--------------------ROUND BEGINS!-------------------------");

        for (Player player : players) {
            String command = "";
            System.out.println(player);
            while (player.isActive() && !command.equals("stand") && player.getHandValue() != BLACK_JACK) {
                System.out.println(player.getName() + ", make an action!");
                command = ui.getCommand();

                Card lastCard = player.getHand().get(player.getHand().size() - 1);
                switch (command) {
                    case "hit":
                        if (player.hasSplitHand()){
                            if (checkHit(splitHand).equals("BUSTED!")){
                                player.setCoins(player.getCoins() - player.getBet());
                                splitHand = null;
                                player.setHasSplitHand(false);
                                System.out.println("Continue with ");
                            }
                        } else {


                            if (checkHit(player).equals("BUSTED!")) {
                                command = "stand";
                            }
                        }
                            System.out.println(player);
                        break;

                    case "split":
                        if (lastCard.getValue() == player.getHand().get(player.getHand().size() - 2).getValue()) {
                            player.setHasSplitHand(true);
                            splitHand = new Player("SPLITHAND", 0, this);
                            splitHand.getHand().add(lastCard);
                            player.getHand().remove(lastCard);
                            splitHand.setBet(player.getBet());
                            //TODO handle more splitHands


                        } else {
                            System.out.println("Split only possible when last two cards values are equal.");
                        }

                }
            }
            nextPlayer();
        }

        while (dealer.getHandValue() <= 16) {
            dealer.hit();
            Card lastCard = dealer.getHand().get(dealer.getHand().size() - 1);
            if (lastCard.getRank() == 'A' && dealer.getHandValue() > 21) {
                lastCard.setValue(1);
            }
        }


        List<Player> winners = new ArrayList<>();
        List<Player> loosers = new ArrayList<>();


        for (Player player : players) {
            if (dealer.getHandValue() > 21) {
                player.looseInsurance();
                if (player.getHandValue() <= 21) {
                    winners.add(player);
                } else {
                    loosers.add(player);
                }
            } else if (dealer.getHandValue() == BLACK_JACK) {
                if (player.getInsurance() > 0) {
                    player.winInsurance();
                }
                player.looseInsurance();
                loosers.add(player);
            } else {
                player.looseInsurance();
                if (player.getHandValue() <= 21 && player.getHandValue() > dealer.getHandValue()) {
                    winners.add(player);
                } else {
                    loosers.add(player);
                }
            }
        }

        for (Player looser : loosers) {
            looser.looseBet();
        }


        System.out.println(dealer);
        System.out.println("Winners:");
        System.out.println("--------------------");
        for (Player winner : winners) {
            winner.winBet();
            String insurance = winner.getInsurance() > 0 ? ("insurance: " + Integer.toString(winner.getInsurance())) : "";
            System.out.println(winner.getName() + ": " + winner.getHand() + " (" + winner.getHandValue() + ") " + insurance);
            System.out.println("coins: " + winner.getCoins() + "\n");
        }
        System.out.println("--------------------");

        System.out.println("Loosers:");
        System.out.println("--------------------");
        for (Player looser : loosers) {
            String insurance = looser.getInsurance() > 0 ? ("insurance: " + Integer.toString(looser.getInsurance())) : "";
            System.out.println(looser.getName() + ": " + looser.getHand() + " (" + looser.getHandValue() + ") " + insurance);
            System.out.println("coins: " + looser.getCoins() + "\n");
        }
        System.out.println("--------------------");

    }

    public String checkHandValue(Player player) {
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
        for (Player player : players) {
            System.out.println(player);
        }
    }

    //FOR TESTING


    public List<Player> getPlayers() {
        return players;
    }

    public Dealer getDealer() {
        return dealer;
    }
}

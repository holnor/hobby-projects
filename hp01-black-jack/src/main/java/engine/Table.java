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
            for (Player player : players) {
                System.out.println(player);
                Card lastCard = player.getHand().get(player.getHand().size() - 1);
                if (lastCard.getRank() == 'A' && player.getHandValue() != BLACK_JACK) {
                    System.out.println(player);
                    System.out.println("Set Ace value (1 OR 11):");
                    lastCard.setValue(ui.getAceValue());
                }
            }
        }
        players.get(0).setActive(true);

        for (Player player : players) {
            player.placeBet(Table.MIN_BET);
        }

        System.out.println(dealer);
        dealer.deal(dealer);


        for (Player player : players) {
            if (dealer.getHandValue() == BLACK_JACK) {
                System.out.println("DEALER HAS BLACK JACK!");
                if (player.getHandValue() == BLACK_JACK) {
                    System.out.println("******* TIE! ********");
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
                        player.hit();
                        if (lastCard.getRank() == 'A'){
                            System.out.println("Set Ace value (1 OR 11):");
                            lastCard.setValue(ui.getAceValue());
                        }
                        String handValue = checkHandValue(player);
                        if (handValue.equals("BUSTED!") || handValue.equals("BLACK JACK!")) {
                            System.out.println("******* " + handValue + " ********");
                            command = "stand";
                        }

                        if(handValue.equals("BUSTED!")){
                            player.looseBet();
                        }

                        System.out.println(player);
                        break;

                    case "insure":
                        if (dealer.getHand().get(0).getRank() == 'A'){
                            player.insure();


                        }else {
                            System.out.println("Last two cards values are not equal");
                        }
                }
            }
            nextPlayer();
        }

        while (dealer.getHandValue() <= 16) {
            dealer.hit();
            Card lastCard = dealer.getHand().get(dealer.getHand().size() - 1);
            if (lastCard.getRank() == 'A' && dealer.getHandValue() > 21){
                lastCard.setValue(1);
            }
        }


        List<Player> winners = new ArrayList<>();
        List<Player> loosers = new ArrayList<>();


        for (Player player : players) {
            if (dealer.getHandValue() > 21) {
                if (player.getHandValue() <= 21) {
                    winners.add(player);
                } else {
                    loosers.add(player);
                }
            } else {
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
            System.out.println(winner.getName() + ": " + winner.getHand() + " (" + winner.getHandValue()+ ")");
            System.out.println("coins: " + winner.getCoins() + "\n");
        }
        System.out.println("--------------------");

        System.out.println("Loosers:");
        System.out.println("--------------------");
        for (Player looser : loosers) {
            System.out.println(looser.getName() + ": " + looser.getHand() + " (" + looser.getHandValue()+ ")");
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

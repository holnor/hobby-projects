package participants;

import cards.Card;
import engine.Table;

import java.util.ArrayList;
import java.util.List;

public abstract class Participant {
    protected String name;
    protected int coins;
    protected int bet;
    protected Table table;

    protected List<Card> hand;
    protected boolean isActive;

    public Participant(String name, Table table) {
        this.name = name;
        this.coins = 500;
        this.bet = 0;
        this.table = table;
        this.hand = new ArrayList<>();
        this.isActive = false;
    }

    public abstract void hit();

    public void stand() {
        isActive = false;
    }


    public void placeBet(int amount) {
        if (amount > coins) {
            bet = amount;
            coins = 0;
        } else {
            bet = amount;
            coins -= bet;
        }
    }

    public void looseBet() {
        bet = 0;
    }

    public void winBet() {
        coins += bet * 2;
        bet = 0;
    }

    public void takeBackBet(){
        coins += bet;
        bet = 0;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isActive() {
        return isActive;
    }

    public int getHandValue(){
        int value = 0;
        for (Card card : hand) {
            value += card.getValue();
        }
        return value;
    }

    public abstract void correctAceValue();

    public String getName() {
        return name;
    }

    public int getBet() {
        return bet;
    }

    //FOR TESTING

    public List<Card> getHand() {
        return hand;
    }

    public int getCoins() {
        return coins;
    }

    @Override
    public String toString() {
        return "--- " + "\u001B[1m" + name + "\u001B[0m" + "-".repeat(30) +
                "\ncoins: " + coins +
                ", bet: " + bet +
                ", isActive: " + isActive +
                ",\nhand: " + hand + "\t value: "+ getHandValue() +"\n" +
                "-".repeat(40) + "\n";
    }
}

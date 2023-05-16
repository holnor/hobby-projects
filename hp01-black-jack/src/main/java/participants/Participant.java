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

    public void hit() {

    }

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

    public String showCards() {
        return hand.toString();
    }


    //FOR TESTING

    public List<Card> getHand() {
        return hand;
    }

    @Override
    public String toString() {
        return "-".repeat(50) +
                "\nname: " + name +
                ", coins: " + coins +
                ", bet: " + bet +
                ", isActive: " + isActive +
                ",\nhand: " + hand + "\n" +
                "-".repeat(50);
    }
}

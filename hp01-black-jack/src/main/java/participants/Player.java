package participants;

import engine.Table;

public class Player extends Participant {
    private int insurance = 0;
    private boolean hasSplitHand = false;


    public Player(String name, int coins, Table table) {
        super(name, table);
        this.coins = coins;
    }

    public void split() {
    }

    public void insure() {
        insurance = bet / 2;
        coins -= insurance;
    }
    public void winInsurance() {
        coins += insurance * 2;
        insurance = 0;
    }
    public void looseInsurance() {
        insurance = 0;
    }





    public int getInsurance() {
        return insurance;
    }

    public void correctAceValue() {

    }

    public boolean hasSplitHand() {
        return hasSplitHand;
    }

    public void setHasSplitHand(boolean hasSplitHand) {
        this.hasSplitHand = hasSplitHand;
    }

    @Override
    public void hit() {
        table.getDealer().deal(this);
    }
}

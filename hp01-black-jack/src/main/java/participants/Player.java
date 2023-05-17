package participants;

import engine.Table;

public class Player extends Participant {
    private int insurance = 0;


    public Player(String name, Table table) {
        super(name, table);
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

    @Override
    public void hit() {
        table.getDealer().deal(this);
    }
}

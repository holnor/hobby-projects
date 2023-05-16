package participants;

import engine.Table;

public class Player extends Participant{


    public Player(String name, Table table) {
        super(name, table);
    }

    public void split(){}

    public void insure(){}

    @Override
    public void hit() {
        table.getDealer().deal(this);
    }
}

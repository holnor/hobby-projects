package participants;

import cards.Card;
import cards.Deck;
import engine.Table;

import java.util.ArrayList;
import java.util.List;

public class Dealer extends Participant {
    private Deck deck;
    private List<Card> pile;

    public Dealer(Table table) {
        super("DEALER", table);
        super.coins = Table.DEALER_COINS;
        this.deck = new Deck();
        this.pile = new ArrayList<>();
    }

    public void deal(Participant player) {
        player.hand.add(deck.getDeck().get(0));
        deck.getDeck().remove(0);
    }

    public void deal(List<Player> players) {
        for (Participant player : players) {
            player.hand.add(deck.getDeck().get(0));
            deck.getDeck().remove(0);
        }
    }

    public Deck getDeck() {
        return deck;
    }

    public List<Card> getPile() {
        return pile;
    }


    @Override
    public void hit() {
        deal(this);
    }

    @Override
    public void correctAceValue() {

    }

    @Override
    public String toString() {
        int value = 0;
        for (Card card : hand) {
            value += card.getValue();
        }
        return "\n----------" + "\u001B[1m" + "DEALER" +  "\u001B[0m" + "----------\n" + hand + "\t value: "+ value + "\n__________________________\n";
    }
}

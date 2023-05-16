package cards;

public class Card {
    private char suit;
    private char rank;
    private int value;

    public Card(char suit, char rank) {
        this.suit = suit;
        this.rank = rank;
        if (Character.isDigit(rank)){
            this.value = Character.getNumericValue(rank);
        } else if (rank == 'A') {
            this.value = 11;
        } else {
            this.value = 10;
        }
    }

    @Override
    public String toString() {
        return "[" + suit + rank + "]" ;
    }

    public int getValue() {
        return value;
    }
}

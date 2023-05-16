package engine;

import java.util.Scanner;

public class UserInterface {
    private Scanner scanner = new Scanner(System.in);


    public int getNumberOfPlayers() {
        System.out.println("Enter how many people would like to play (1-6):");
        return scanner.nextInt();
    }

    public int getAceValue(){
        int value = scanner.nextInt();
        if (value != 1 && value != 11){
            System.out.println("Enter valid value (1 OR 11)");
            return getAceValue();
        }
        return value;
    }

    public String getNames() {
        return scanner.next();
    }

    public String getCommand() {
        String input = scanner.next();

        switch (input) {
            case "hit":
            case "stand":
            case "bet":
            case "split":
            case "insure":
                return input;
            default:
                return "invalid";
        }
    }
}

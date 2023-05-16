package engine;

import java.util.Scanner;

public class UserInterface {
    private Scanner scanner = new Scanner(System.in);


    public int getNumberOfPlayers() {
        System.out.println("Enter how many people would like to play (1-6):");
        return scanner.nextInt();
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

import engine.Table;
import engine.UserInterface;

public class App {
    public static void main(String[] args) {
        UserInterface ui = new UserInterface();
        Table bj = new Table(ui.getNumberOfPlayers());
        System.out.println(bj);
    }
}

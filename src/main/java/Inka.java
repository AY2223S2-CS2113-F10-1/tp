import utils.parser.Parser;
import utils.userinterface.UserInterface;

public class Inka {
    private final UserInterface ui;
    private final Parser parser;
    public Inka() {
        ui = new UserInterface();
        parser = new Parser();
    }

    public void run() {
        ui.printGreeting();

        //fill in the method here
        //while(parser.getIsExecuting()) {
        //  String fullCommand = ui.getCommand();
        //}

        ui.printBye();
    }

    public static void main(String[] args) {
        new Inka().run();
    }
}

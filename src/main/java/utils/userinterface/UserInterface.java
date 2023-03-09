package utils.userinterface;

import utils.enums.StringArt;

import java.util.Scanner;

public class UserInterface {
    private static final int LINE_LENGTH = 100;
    private static final String INKA_ART =
                ".___        __            \n" +
                "|   | ____ |  | _______   \n" +
                "|   |/    \\|  |/ /\\__  \\  \n" +
                "|   |   |  \\    <  / __ \\_\n" +
                "|___|___|  /__|_ \\(____  /\n" +
                "         \\/     \\/     \\/ ";

    private static final String BYE_ART =
                " ____  _  _  ____    _   \n" +
                "(  _ \\( \\/ )(  __)  / \\  \n" +
                " ) _ ( )  /  ) _)   \\_/  \n" +
                "(____/(__/  (____)  (_) ";
    private final Scanner scanner;
    public UserInterface() {
        scanner = new Scanner(System.in);
    }

    public String getCommand() {
        return scanner.nextLine();
    }

    public void printLine() {
        System.out.println("_".repeat(LINE_LENGTH));
    }

    public void printGreeting() {
        printLine();
        System.out.println(StringArt.INKA.art);
        System.out.println("Welcome to Inka ! Say no more to failing exams as Inka will be your lord and saviour!");
        printLine();
    }

    public void printBye() {
        printLine();
        System.out.println(StringArt.BYE.art);
        System.out.println("\n Bye! All the best for your exams man!!!");
        printLine();
    }
}

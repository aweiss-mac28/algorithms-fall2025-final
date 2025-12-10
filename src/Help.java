import java.util.List;
import java.util.Scanner;

public class Help {
    private static Scanner helpScanner;
    private static List<String> yeses = List.of("y", "Y", "yes", "Yes");

    public static void getHelp() {
        helpScanner = new Scanner(System.in);
        System.out.println("What do you need help with?");
        availableHelp();
        String helpType = helpScanner.nextLine();
        System.out.println();
        if (helpType.equalsIgnoreCase("commands")) {
            commandHelp();
        }
        if (helpType.equalsIgnoreCase("guessing")) {
            guessingHelp();
        }
        if (helpType.equalsIgnoreCase("gameinfo")) {
            gameInfoHelp();
        }
    }

    public static void availableHelp() {
        System.out.println("Available help: commands, guessing, gameinfo");
    }

    public static void commandHelp() {
        helpScanner = new Scanner(System.in);
        String commandHelpText = "command\t\tusage\n" +
                                    "-------\t\t---------------------------------------\n" +
                                    "help\t\tprovides help documentation\n" +
                                    "reset\t\tresets game\n" +
                                    "score\t\treturns current total wins and losses\n" +
                                    "previous\treturns previous guesses and their marks\n" +
                                    "visualize\topens a window with a visualization of the current game\n" +
                                    "-------\t\t---------------------------------------\n"
                                    ;
        System.out.println(commandHelpText);
        System.out.println("Would you like more help?");
        String moreHelp = helpScanner.nextLine();
        if (yeses.contains(moreHelp)) {
            getHelp();
        }
    }

    public static void guessingHelp() {
        helpScanner = new Scanner(System.in);
        String guessingHelpText = "In order to make a guess, you must enter a series of 4 valid colors. " +
                                    "The valid colors are: " +
                                    "Red, Orange, Yellow, Green, Blue, Purple\n" +
                                    "Here's a sample guess: `Purple Green Yellow Blue`\n"
                                    ;
        System.out.println(guessingHelpText);
        System.out.println("Would you like more help?");
        String moreHelp = helpScanner.nextLine();
        if (yeses.contains(moreHelp)) {
            getHelp();
        }
    }

    public static void gameInfoHelp() {
        helpScanner = new Scanner(System.in);
        String gameInfoHelpText = "At the beginning of the game, the program chooses a secret “code” composed of 4 colors." +
                                    "The object of the game is to guess the code." +
                                    "You have 10 rounds to guess the code (see guessing help for details)." + 
                                    "After each guess, your guess will be \"graded\" using black and white marks." +
                                    "A black mark is awarded for each position of the guess that matches the code." +
                                    "A white mark is awarded for positions where you guessed the correct color," +
                                    "but placed it in the wrong position.\n" +
                                    "If the code you're trying to guess is:\n" +
                                    "`Red Blue Purple Red`\n" +
                                    "and your guess is:\n" +
                                    "`Purple Green Orange Red`\n" +
                                    "You will recieve one black mark and one white mark, " +
                                    "since the final Red pin is correct and the Purple pin, while correct, must move.\n" +
                                    "Once you get four black marks in a guess you win!\n"
                                    ;
        System.out.println(gameInfoHelpText);
        System.out.println("Would you like more help?");
        String moreHelp = helpScanner.nextLine();
        if (yeses.contains(moreHelp)) {
            getHelp();
        }
    }
}

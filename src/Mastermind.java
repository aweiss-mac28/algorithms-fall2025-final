
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import edu.macalester.graphics.CanvasWindow;

public class Mastermind {
    private final static int WINDOW_WIDTH = 400;
    private final static int WINDOW_HEIGHT = 600;
    public final static int MAX_GUESSES = 10;
    private static Scanner scan;
    private static CanvasWindow canvas;

    private static int totalWins;
    private static int totalLosses;
    private static Answer answer;
    private static int guessesMade;
    private static ArrayList<Guess> guessList;
    private static ArrayList<Marks> marksList;
    private static Visualizer visualizer;
    
    public static void main(String[] args) {
        new Mastermind().play();
    }

    public Mastermind() {
        totalWins = 0;
        totalLosses = 0;
        guessesMade = 0;
        answer = new Answer();
        guessList = new ArrayList<>();
        marksList = new ArrayList<>();
        scan = new Scanner(System.in);
    }

    public void play() {
        System.out.println("~~~~~ NEW GAME STARTED ~~~~~");
        while (guessList.size() < MAX_GUESSES && !gameWon()) {
            System.out.println("Make a guess (or enter another command): ");
            String userInput = scan.nextLine();
            Marks marks = Mastermind.processUserInput(userInput);
            marksList.add(marks);
            System.out.println(marks.getMarks());
            if (visualizer != null) {
                visualizer.update(guessList, marksList);
                canvas.draw();
            }
            if (gameWon()) {
                totalWins ++;
                System.out.println("You won!");
            }
        }
        if (!gameWon()) {
            System.out.println("You lost :(");
            totalLosses ++;
        }
        System.out.println("Play again? (Y/N)");
        if (scan.nextLine().equalsIgnoreCase("Y")) {
            reset();
        }
        scan.close();
    }

    public static Marks processUserInput(String userInput) {
        while (userInput.equalsIgnoreCase("help")) {
            Help.getHelp();
            System.out.println("Now go forth and make a guess (or enter a command)!");
            userInput = scan.nextLine();
        }
        while (userInput.equalsIgnoreCase("score")) {
            getScore();            
            System.out.println("Make a guess (or enter another command): ");
            userInput = scan.nextLine();
        } 
        while (userInput.equalsIgnoreCase("previous")) {
            previousGuesses();
            System.out.println("Make a guess (or enter another command): ");
            userInput = scan.nextLine();
        }
        while (userInput.equalsIgnoreCase("visualize")) {
            visualize();
            System.out.println("Make a guess (or enter another command): ");
            userInput = scan.nextLine();
        }
        if (userInput.equalsIgnoreCase("reset")) {
            reset();
        }
        ArrayList<String> userGuess = new ArrayList<>(Arrays.asList(userInput.split(" ")));
        CodePin guessPin1 = new CodePin(userGuess.get(0));
        CodePin guessPin2 = new CodePin(userGuess.get(1));
        CodePin guessPin3 = new CodePin(userGuess.get(2));
        CodePin guessPin4 = new CodePin(userGuess.get(3));
        Guess guess = new Guess(guessPin1, guessPin2, guessPin3, guessPin4);
        guessList.add(guess);
        guessesMade ++;
        return new Marks(guess, answer);
    }

    public static void getScore() {
        System.out.println("Your current score is: " + totalWins + " wins and " + totalLosses + " losses.");
    }

    public static void reset() {
        new Mastermind().play();
    }

    public static void previousGuesses() {
        for (int i = 0; i < guessesMade; i++) {
            System.out.print("Guess " + i + ": " + guessList.get(i).getSequenceAsString());
            System.out.println("\t--- " + marksList.get(i).getMarks());
        }
    }

    public static void visualize() {
        canvas = new CanvasWindow("Mastermind Game", WINDOW_WIDTH, WINDOW_HEIGHT);
        canvas.setBackground(Color.BLACK);

        visualizer = new Visualizer(WINDOW_WIDTH, WINDOW_HEIGHT);
        visualizer.update(guessList, marksList);
        canvas.add(visualizer);
        canvas.draw();
    }

    public boolean gameWon() {
        if (guessesMade == 0) {
            return false;
        }
        if (answer.getSequenceAsString().equals(guessList.get(guessesMade - 1).getSequenceAsString())) {
            return true;
        }
        return false;
    }
}

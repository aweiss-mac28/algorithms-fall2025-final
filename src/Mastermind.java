import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.ui.Button;
import edu.macalester.graphics.GraphicsText;

public class Mastermind {
    private final static int WINDOW_WIDTH = 300;
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

    private static Button redButton;
    private static Button orangeButton;
    private static Button yellowButton;
    private static Button greenButton;
    private static Button blueButton;
    private static Button purpleButton;
    private static int buttonCount;
    private static String strButtonGuess;
    
    public static void main(String[] args) {
        Mastermind mm = new Mastermind();
        scan = new Scanner(System.in);
        gameChoice(mm);
    }

    public Mastermind() {
        totalWins = 0;
        totalLosses = 0;
        guessesMade = 0;
        answer = new Answer();
        guessList = new ArrayList<>();
        marksList = new ArrayList<>();
    }

    public void playClick() {
        buttonCount = 0;
        strButtonGuess = "";
        visualize();
    }

    public void playText() {
        System.out.println("~~~~~ NEW GAME STARTED ~~~~~");
        while (guessList.size() < MAX_GUESSES && !gameWon()) {
            System.out.println("Make a guess (or enter another command such as 'help'): ");
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
        } else if (canvas != null) {
            canvas.closeWindow();
        }
    }

    private static void gameChoice(Mastermind mm) {
        System.out.println("How do you want to play?\nType 't' for text or 'v' for visual.");
        String userInput = scan.nextLine();
        List<String> tv = List.of("t","v");
        while (!tv.contains(userInput)) {
            System.out.println("That's not a valid input. Please enter 't' or 'v'.");
            userInput = scan.nextLine();
        }
        if (userInput.equalsIgnoreCase("t")) {
            mm.playText();
        } else if (userInput.equalsIgnoreCase("v")) {
            mm.playClick();
        }
    }

    public static void winCheck() {
        if (gameWon()) {
            canvas.pause(4000);
            totalWins ++;
            System.out.println("You won!");
            canvas.removeAll();
            GraphicsText win = new GraphicsText("You win!");
            win.setFillColor(Color.WHITE);
            win.setCenter(WINDOW_WIDTH/2, WINDOW_HEIGHT/2);
            canvas.add(win);
            canvas.draw();
        }

        if (!gameWon() && guessList.size() >= MAX_GUESSES) {
            System.out.println("You lost :(");
            totalLosses ++;
            canvas.removeAll();
            GraphicsText lose = new GraphicsText("You lose :(");
            lose.setFillColor(Color.WHITE);
            lose.setCenter(WINDOW_WIDTH/2, WINDOW_HEIGHT/2);
            canvas.add(lose);
            canvas.draw();
        }
    }

    public static void setUpButtons() {
        redButton = new Button("Red");
        orangeButton = new Button("Orange");
        yellowButton = new Button("Yellow");
        greenButton = new Button("Green");
        blueButton = new Button("Blue");
        purpleButton = new Button("Purple");

        redButton.setPosition(10, 10);
        orangeButton.setPosition(110, 10);
        yellowButton.setPosition(210, 10);
        greenButton.setPosition(10, 40);
        blueButton.setPosition(110, 40);
        purpleButton.setPosition(210, 40);

        canvas.add(redButton);
        canvas.add(orangeButton);
        canvas.add(yellowButton);
        canvas.add(greenButton);
        canvas.add(blueButton);
        canvas.add(purpleButton);

        redButton.onClick(() -> handleButtonPress("red"));
        orangeButton.onClick(() -> handleButtonPress("orange"));
        yellowButton.onClick(() -> handleButtonPress("yellow"));
        greenButton.onClick(() -> handleButtonPress("green"));
        blueButton.onClick(() -> handleButtonPress("blue"));
        purpleButton.onClick(() -> handleButtonPress("purple"));
    }

    public static void handleButtonPress(String color) {
        buttonCount++;
        if (strButtonGuess.equals("")) {
            strButtonGuess += color;
        } else {
            strButtonGuess += " " + color;
        }
        if (buttonCount == 4) {
            Marks marks = processUserInput(strButtonGuess);
            marksList.add(marks);
            guessList.size();
            visualizer.update(guessList, marksList);
            canvas.draw();
            buttonCount = 0;
            strButtonGuess = "";
            winCheck();
        } else {
            createGuess(strButtonGuess);
            visualizer.update(guessList, marksList);
            canvas.draw();
        }
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
        Guess guess = createGuess(userInput);
        guessesMade++;
        return new Marks(guess, answer);
    }

    public static void getScore() {
        System.out.println("Your current score is: " + totalWins + " wins and " + totalLosses + " losses.");
    }

    public static void reset() {
        new Mastermind().playText();
    }

    public static void previousGuesses() {
        for (int i = 0; i < guessesMade; i++) {
            System.out.print("Guess " + i + ": " + guessList.get(i).getSequenceAsString());
            System.out.println("\t--- " + marksList.get(i).getMarks());
        }
    }

    public static void visualize() {
        if (canvas != null) {
            canvas.closeWindow();
        }

        canvas = new CanvasWindow("Mastermind Game", WINDOW_WIDTH, WINDOW_HEIGHT);
        canvas.setBackground(Color.BLACK);

        visualizer = new Visualizer(WINDOW_WIDTH, WINDOW_HEIGHT);
        visualizer.update(guessList, marksList);
        canvas.add(visualizer);

        setUpButtons();

        canvas.draw();
    }

    public static boolean gameWon() {
        if (guessesMade == 0) {
            return false;
        }
        if (answer.getSequenceAsString().equals(guessList.get(guessesMade - 1).getSequenceAsString())) {
            return true;
        }
        return false;
    }

    private static Guess createGuess(String guessString) {
        ArrayList<String> userGuess = new ArrayList<>(Arrays.asList(guessString.split(" ")));
        int numPins = userGuess.size();
        Guess guess = null;
        if (numPins == 1) {
            guess = new Guess(new CodePin(userGuess.get(0)));
        } else if (numPins == 2) {
            guess = new Guess(
                new CodePin(userGuess.get(0)), 
                new CodePin(userGuess.get(1)));
        } else if (numPins == 3) {
            guess = new Guess(
                new CodePin(userGuess.get(0)), 
                new CodePin(userGuess.get(1)), 
                new CodePin(userGuess.get(2)));
        } else {
            // Case when you have a complete guess.
            guess = new Guess(
                new CodePin(userGuess.get(0)), 
                new CodePin(userGuess.get(1)), 
                new CodePin(userGuess.get(2)), 
                new CodePin(userGuess.get(3)));
        }
        updateGuessList(guess);
        return guess;
    }

    private static void updateGuessList(Guess guess) {
        if (guessList.isEmpty()) {
            guessList.add(guess);
        } else if (guess.size() > guessList.getLast().size()) {
            guessList.set(guessesMade, guess);
        } else {
            guessList.add(guess);
        }
    }
}

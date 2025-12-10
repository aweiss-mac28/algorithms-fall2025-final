import java.awt.Color;

import edu.macalester.graphics.*;
import edu.macalester.graphics.ui.Button;;

public class Main {
    private final static int WINDOW_WIDTH = 300;
    private final static int WINDOW_HEIGHT = 100;
    private static CanvasWindow canvas;
    private static Button knuthButton;
    private static Button playButton;

    public static void main(String[] args) {
        // runKnuth();
        // playMastermind();
        startMenu();
    }

    private static void startMenu() {
        if (canvas != null) {
            canvas.closeWindow();
        }
        canvas = new CanvasWindow("Start Menu", WINDOW_WIDTH, WINDOW_HEIGHT);
        canvas.setBackground(Color.LIGHT_GRAY);
        addButtons();
        canvas.draw();
    }

    private static void addButtons() {
        knuthButton = new Button("Run Knuth Algorithm");
        playButton = new Button("Play Mastermind");
        knuthButton.setPosition(WINDOW_WIDTH / 2 - knuthButton.getWidth() / 2,WINDOW_HEIGHT / 2 - knuthButton.getHeight() * 1.2);
        playButton.setPosition(WINDOW_WIDTH / 2 - playButton.getWidth() / 2, knuthButton.getY() + knuthButton.getHeight() + 10);
        canvas.add(knuthButton);
        canvas.add(playButton);
        knuthButton.onClick(() -> runKnuth());
        playButton.onClick(() -> playMastermind());
    }

    private static void runKnuth() {
        KnuthAlgorithm kn = new KnuthAlgorithm();
        kn.runGame();
    }

    private static void playMastermind() {
        new Mastermind();
    }
}

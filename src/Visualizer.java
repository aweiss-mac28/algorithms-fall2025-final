
import java.awt.Color;
import java.util.ArrayList;

import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Rectangle;

public class Visualizer extends GraphicsGroup {
    private GraphicsGroup guessPins = new GraphicsGroup();
    private GraphicsGroup marksPins = new GraphicsGroup();
    private int width;
    private int height;
    private GameBoard gameBoard;

    public Visualizer(int width, int height) {
        this.width = width;
        this.height = height;
        gameBoard = new GameBoard();
        gameBoard.setCenter(width / 2, height / 2);
        this.add(gameBoard);
        this.add(guessPins);
        this.add(marksPins);
    }


    public void update(ArrayList<Guess> guessList, ArrayList<Marks> marksList) {
        showGuesses(guessList);
        showMarks(marksList);
    }

    private void showGuesses(ArrayList<Guess> guesses) {
        guessPins.removeAll();

        int i = 0;
        for (Guess guess : guesses) {
            double xPos = 0;
            double yPos = i * CodePin.PIN_SIZE * 2;
            GraphicsGroup guessGraphics = guess.getSequenceGraphics();
            guessPins.add(guessGraphics, xPos, yPos);
            i++;
        }
        guessPins.setPosition((width / 4.6) - (CodePin.PIN_SIZE), height / 5.5);
    }

    private void showMarks(ArrayList<Marks> marks) {
         marksPins.removeAll();

        int i = 0;
        for (Marks mark : marks) {
            double xPos = 0;
            double yPos = i * CodePin.PIN_SIZE * 2;
            GraphicsGroup markGraphics = mark.getMarksGraphics();
            marksPins.add(markGraphics, xPos, yPos);
            i++;
        }
        marksPins.setPosition(CodePin.PIN_SIZE * 10.1, CodePin.PIN_SIZE * 5.6);
    }
}

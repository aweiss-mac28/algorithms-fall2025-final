
import java.awt.Color;
import java.util.ArrayList;

import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Rectangle;

public class Visualizer extends GraphicsGroup {
    private GraphicsGroup guessPins = new GraphicsGroup();
    private GraphicsGroup marksPins = new GraphicsGroup();
    private int width;
    private int height;

    public Visualizer(int width, int height) {
        this.width = width;
        this.height = height;
        GameBoard gameBoard = new GameBoard();
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

        //GraphicsGroup guessPins = new GraphicsGroup();
        //this.add(guessPins);

        int i = 0;
        for (Guess guess : guesses) {
            double xPos = 0;
            double yPos = i * CodePin.PIN_SIZE * 2;
            GraphicsGroup guessGraphics = guess.getSequenceGraphics();
            guessPins.add(guessGraphics, xPos, yPos);
            i++;
        }
        // for (int n = 0; n < (Mastermind.MAX_GUESSES - i); n++) { //changed n > (Mastermind.MAX_GUESSES - i) to changed n < (Mastermind.MAX_GUESSES - i) elyse
        //     double xPos = CodePin.PIN_SIZE * 1.5;
        //     double yPos = (n + i) * CodePin.PIN_SIZE * 2;
        //     Rectangle filler = 
        //         new Rectangle(0, 0, CodePin.PIN_SIZE * 6, CodePin.PIN_SIZE);
        //     filler.setFilled(false);
        //     // filler.setStroked(false);
        //     filler.setStrokeColor(Color.white);
        //     guessPins.add(filler, xPos, yPos);
        // }
        guessPins.setCenter((width / 2) - (CodePin.PIN_SIZE * 2), height / 2);
    }

    private void showMarks(ArrayList<Marks> marks) {
         marksPins.removeAll();

        //GraphicsGroup marksPins = new GraphicsGroup(); 
        //this.add(marksPins);

        int i = 0;
        for (Marks mark : marks) {
            double xPos = 0;
            double yPos = i * CodePin.PIN_SIZE * 2;
            GraphicsGroup markGraphics = mark.getMarksGraphics();
            marksPins.add(markGraphics, xPos, yPos);
            i++;
        }
        marksPins.setCenter((width / 2) + CodePin.PIN_SIZE * 3, (height / 2) - (CodePin.PIN_SIZE * (5 - marks.size())));
    }
}



import java.awt.Color;

import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Rectangle;

public class GameBoard extends GraphicsGroup {
    private double boardHeight;
    private double boardWidth;

    public GameBoard() {
        boardHeight = 20 * CodePin.PIN_SIZE;
        boardWidth = 8 * CodePin.PIN_SIZE;
        createBoard();
        createGuessPinHoles();
        createMarkPinHoles();
    }

    private void createBoard() {
        Rectangle board = new Rectangle(0, 0, boardWidth, boardHeight);
        board.setFillColor(Color.LIGHT_GRAY);
        this.add(board);
        
    }

    private void createGuessPinHoles() {
        GraphicsGroup guessPinHoles = new GraphicsGroup();
        for (int i = 0; i < Mastermind.MAX_GUESSES; i++) {
            double xPos = 0;
            double yPos = i * CodePin.PIN_SIZE * 2;
            for (int j = 0; j < 4; j++) {
                xPos += CodePin.PIN_SIZE * 1.5;
                Ellipse pinHole = new Ellipse(xPos, yPos, CodePin.PIN_SIZE, CodePin.PIN_SIZE);
                pinHole.setFillColor(Color.GRAY);
                guessPinHoles.add(pinHole);
        }
        }
        guessPinHoles.setCenter(CodePin.PIN_SIZE * 3, boardHeight / 2);
        this.add(guessPinHoles);
    }

    private void createMarkPinHoles() {
        GraphicsGroup markPinHoles = new GraphicsGroup();
        for (int i = 0; i < Mastermind.MAX_GUESSES; i++) {
            double xPos = 0;
            double yPos = i * CodePin.PIN_SIZE * 2;
            for (int j = 0; j < 4; j++) {
                if (xPos > MarkPin.PIN_SIZE * 3) {
                    xPos = 0;
                    yPos += MarkPin.PIN_SIZE * 2;
                }
                Ellipse pinHole = new Ellipse(xPos, yPos, MarkPin.PIN_SIZE, MarkPin.PIN_SIZE);
                pinHole.setFillColor(Color.GRAY);
                markPinHoles.add(pinHole);
                xPos += MarkPin.PIN_SIZE * 2;
            }
        }
        markPinHoles.setCenter(CodePin.PIN_SIZE * 7, boardHeight / 2);
        this.add(markPinHoles);
    }
}

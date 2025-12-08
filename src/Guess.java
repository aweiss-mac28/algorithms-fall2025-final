import java.util.ArrayList;

import edu.macalester.graphics.GraphicsGroup;

public class Guess implements Sequence {
    private ArrayList<CodePin> guess;
    private GraphicsGroup guessPins;

    public Guess(CodePin pin1, CodePin pin2, CodePin pin3, CodePin pin4) {
        guess = new ArrayList<>();
        guess.add(pin1);
        guess.add(pin2);
        guess.add(pin3);
        guess.add(pin4);

        guessPins = new GraphicsGroup();
        double xPos = 0;
        double yPos = 0;
        for (CodePin pin : guess) {
            xPos += CodePin.PIN_SIZE * 1.5;
            guessPins.add(pin.getPinGraphic(), xPos, yPos);
        }
    }

    public ArrayList<CodePin> getSequence() {
        return guess;
    }

    public String getSequenceAsString() {
        String sequenceAsString = "";
        for (CodePin pin : guess) {
            sequenceAsString += pin.getColor() + " ";
        }
        return sequenceAsString;
    }

    public GraphicsGroup getSequenceGraphics(){
        return guessPins;
    }
}

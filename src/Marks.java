
import java.util.ArrayList;

import edu.macalester.graphics.GraphicsGroup;

public class Marks {
    private int numBlack;
    private int numWhite;
    private ArrayList<MarkPin> markPinList;
    private GraphicsGroup markPins;

    public Marks(Sequence guess, Sequence answer) {
        numBlack = 0;
        numWhite = 0;
        markPinList = new ArrayList<>();
        ArrayList<CodePin> guessSequence = guess.getSequence();
        ArrayList<CodePin> answerSequence = answer.getSequence();
        ArrayList<CodePin> incorrect = new ArrayList<>(answerSequence);
        int i = 0;
        for (CodePin pin : guessSequence) {
            if (pin.getColor().equals(answerSequence.get(i).getColor())) {
                numBlack ++;
                //markPinList.add(new MarkPin("black"));
                if(incorrect.get(i) == null){
                    numWhite --;
                }
                incorrect.set(i, null);
            }
            else {
                for (int j = 0; j < incorrect.size(); j++) {
                    CodePin otherPin = incorrect.get(j);
                    if (otherPin != null && pin.getColor().equals(otherPin.getColor())) {
                        numWhite++;
                        //markPinList.add(new MarkPin("white"));
                        incorrect.set(j, null);
                        break;
                    }
                }
            }
            i++;
        }
        for (int k = 0; k < numBlack; k++) {
            markPinList.add(new MarkPin("black"));
        }

        for (int l = 0; l < numWhite; l++) {
            markPinList.add(new MarkPin("white"));
        } 
        //last 2 for loops control order for marks, black before white

        markPins = new GraphicsGroup();
        double xPos = 0;
        double yPos = 0;
        for (MarkPin pin : markPinList) {
            if (xPos > MarkPin.PIN_SIZE * 2) {
            xPos = 0;
            yPos += MarkPin.PIN_SIZE * 2;
            }
            markPins.add(pin.getPinGraphic(), xPos, yPos);
            xPos += MarkPin.PIN_SIZE * 2;
        }
    }

    public String getMarks() {
        return ("Your guess got " + numBlack + " black marks and " + numWhite + " white marks.");
    }

    public GraphicsGroup getMarksGraphics() {
        return markPins;
    }
}

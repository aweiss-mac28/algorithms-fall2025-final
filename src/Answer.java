
import java.util.ArrayList;

import edu.macalester.graphics.GraphicsGroup;

public class Answer implements Sequence {
    private ArrayList<CodePin> answer;
    private GraphicsGroup answerPins;

    public Answer() {
        answer = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            answer.add(CodePin.getRandomCodePin());
        }

        answerPins = new GraphicsGroup();
        double xPos = 0;
        double yPos = 0;
        for (CodePin pin : answer) {
            xPos += CodePin.PIN_SIZE * 1.5;
            answerPins.add(pin.getPinGraphic(), xPos, yPos);
        }
    }

    public Answer(String givenAnswer){
         answer = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            answer.add(new CodePin(ColorFormat.numberToColor(Integer.parseInt(givenAnswer.substring(i, i+1)))));
        }
    }

    public ArrayList<CodePin> getSequence() {
        return answer;
    }

    public String getSequenceAsString() {
        String sequenceAsString = "";
        for (CodePin pin : answer) {
            sequenceAsString += pin.getColor() + " ";
        }
        return sequenceAsString;
    }

    public GraphicsGroup getSequenceGraphics() {
        return answerPins;
    }
}

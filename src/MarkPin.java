
import java.awt.Color;
import java.util.List;

import edu.macalester.graphics.Ellipse;

public class MarkPin implements Pin {
    public static final double PIN_SIZE = 5;
    List<String> markPinColors = List.of("black", "white");
    private String pinColor;
    private Ellipse pinGraphic;

    public MarkPin(String color) {
        if (!markPinColors.contains(color)) {
            throw new IllegalArgumentException("That's not a valid pin color.");
        }
        pinColor = color;
        pinGraphic = new Ellipse(0, 0, PIN_SIZE, PIN_SIZE);
        setGraphicColor(color);
    }

    private void setGraphicColor(String color) {
        Color fillColor;
        if (color.equalsIgnoreCase("black")) {
            fillColor = Color.BLACK;
        } else {
            fillColor = Color.WHITE;
        }
        this.pinGraphic.setFillColor(fillColor);
    }

    public String getColor() {
        return pinColor;
    }

    public Ellipse getPinGraphic() {
        return pinGraphic;
    }
}


import java.awt.Color;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import edu.macalester.graphics.Ellipse;

public class CodePin implements Pin {
    public static final double PIN_SIZE = 20;
    public static final List<String> colorList = List.of("Red", "Orange", "Yellow", "Green", "Blue", "Purple");

    private Scanner scan;
    private String pinColor;
    private Ellipse pinGraphic;

    public CodePin(String color) {
        color = ColorFormat.toColorFormat(color);
        while (!colorList.contains(color)) {
            scan = new Scanner(System.in);
            System.out.println("The color \"" + color + "\" is not a valid color. Please enter a valid color.");
            String newInput = scan.next();
            if (newInput.equalsIgnoreCase("help")) {
                Help.getHelp();
                System.out.println("Please enter a color to replace \"" + color + "\".");
                color = scan.next();
            } else {
                color = newInput;
            }
            
        }
        pinColor = color;
        pinGraphic = new Ellipse(0, 0, PIN_SIZE, PIN_SIZE);
        setGraphicColor(color);
    }

    private void setGraphicColor(String color) {
        Color fillColor;
        if (color.equalsIgnoreCase("red")) {
            fillColor = Color.RED;
        } else if (color.equalsIgnoreCase("orange")) {
            fillColor = Color.ORANGE;
        } else if (color.equalsIgnoreCase("yellow")) {
            fillColor = Color.YELLOW;
        } else if (color.equalsIgnoreCase("green")) {
            fillColor = Color.GREEN;
        } else if (color.equalsIgnoreCase("blue")) {
            fillColor = Color.BLUE;
        } else {
            fillColor = Color.MAGENTA;
        }
        this.pinGraphic.setFillColor(fillColor);
    }

    public String getColor() {
        return pinColor;
    }

    public static CodePin getRandomCodePin() {
        Random rand = new Random();
        String randomColor = colorList.get(rand.nextInt(colorList.size()));
        CodePin randomCodePin = new CodePin(randomColor);
        return randomCodePin;
    }

    public Ellipse getPinGraphic() {
        return pinGraphic;
    }
}

/**
 * Holds two conversion methods-- one which formats a color string to the proper format 
 * and one which translates a color int to a color string. 
 * @authors~~~
 * 
 */
public class ColorFormat {

    public static String toColorFormat(String color) {
        String colorFormatString = "";
        color = color.toLowerCase();
        String firstLetter = color.substring(0, 1);
        colorFormatString = color;
        colorFormatString = colorFormatString.replaceFirst(firstLetter, firstLetter.toUpperCase());
        return colorFormatString;
    }

    public static String numberToColor(int num) {
        return CodePin.colorList.get(num - 1); 
    }
}

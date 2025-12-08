public class ColorFormat {
    public static String toColorFormat(String color) {
        String colorFormatString = "";
        color = color.toLowerCase();
        String firstLetter = color.substring(0, 1);
        colorFormatString = color;
        colorFormatString = colorFormatString.replaceFirst(firstLetter, firstLetter.toUpperCase());
        return colorFormatString;
    }
}

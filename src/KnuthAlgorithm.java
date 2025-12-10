import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.GraphicsText;

public class KnuthAlgorithm {
    private static Scanner scan;
    private static ArrayList<Guess> guessList ;
    private static ArrayList<Marks> marksList;
    private static Visualizer visualizer;
    private static Answer answer;
    private static boolean gameWon;
    private static ArrayList <String> possibleCodes;
    private static ArrayList <String> allCodes;
    private static GraphicsGroup answerGraphic;
    private final static int WINDOW_WIDTH = 300;
    private final static int WINDOW_HEIGHT = 600;
    private static CanvasWindow canvas;

    public KnuthAlgorithm() {
        
        answer = new Answer();
        answerGraphic = answer.getSequenceGraphics();
        System.out.println("secret: " + answer.getSequenceAsString() );
        gameWon = false;

        possibleCodes = generateCodes();
        allCodes = new ArrayList<>(possibleCodes);
        guessList = new ArrayList<>();
        marksList = new ArrayList<>();


    }

    public void runGame(){
       
        processTurn("1122");
        visualize();
        while(!gameWon){
            String nextGuess = chooseNextGuess();
            processTurn("" + nextGuess);
              if (visualizer != null) {
                visualizer.update(guessList, marksList);
                canvas.pause(2000);
                canvas.draw();
            }
        }
    }
      public static void visualize() {
        if (canvas != null) {
            canvas.closeWindow();
        }

        canvas = new CanvasWindow("Mastermind Game", WINDOW_WIDTH, WINDOW_HEIGHT);
        canvas.setBackground(Color.BLACK);
        answerGraphic.setCenter(WINDOW_WIDTH/2-15, 60);
        canvas.add(answerGraphic);
        GraphicsText answerText = new GraphicsText("SECRET ANSWER");
        answerText.setFillColor(Color.white);
        answerText.setCenter(WINDOW_WIDTH/2-15, 40);
        canvas.add(answerText);
        visualizer = new Visualizer(WINDOW_WIDTH, WINDOW_HEIGHT);
        visualizer.update(guessList, marksList);
        canvas.add(visualizer);


        canvas.draw();
    }

    public static void processTurn(String guess){
        System.out.println("guess: " + guess);
        Marks marks = processUserInput(guess);
        System.out.println(marks.getMarks());
        marksList.add(marks);
        String markResult = formatMarks(marks.getMarks());
        int numblack = Integer.parseInt(markResult.substring(1));
        if(numblack == 4){
            System.out.println("Game won with " + guessList.size() + " guesses!");
            gameWon = true;
        }
        else{
            allCodes.remove(guess);
            possibleCodes.remove(guess);
            ArrayList<String> newList = new ArrayList<>();
            for(String code : possibleCodes){
                if(checkIfPossible(marks.getMarks(), guess, code)){
                    newList.add(code);
                }
            }
            possibleCodes = newList;
        }
    }

    public static String chooseNextGuess() {
        int bestScore = Integer.MAX_VALUE;
        ArrayList<String> bestCandidates = new ArrayList<>();

        for (String guess : allCodes) {
            int maxGroup = scoreGuess(guess, possibleCodes);  

            if (maxGroup < bestScore) {
                bestScore = maxGroup;
                bestCandidates.clear();
                bestCandidates.add(guess);
            } else if (maxGroup == bestScore) {
                bestCandidates.add(guess);
            }
        }

        for (String g : bestCandidates) {
            if (possibleCodes.contains(g)) {
                return g;
            }
        }
        return bestCandidates.get(0);
    }

    //if guesslist.get(guesslist.size()-1) produced a certain mark, decides whether a hypothetical 
    // future guess could have been the secret code to produce those marks.
    public static boolean checkIfPossible(String marks, String prevGuess, String possibleGuess){
        Marks possibleGuessMarks = processHypotheticalInput(prevGuess, possibleGuess);
        String possibleResult = possibleGuessMarks.getMarks();
        if(marks.equals(possibleResult)){
           return true;
        }
        return false;
    }

    public static String formatMarks(String phrase){
        String numwhite = phrase.substring(33, 34);
        String numblack = phrase.substring(15,16);
        return numwhite + "" + numblack;
    }

    public static Marks processUserInput(String userInput) {
        ArrayList<String> userGuess = new ArrayList<>();
        for(int i = 0; i<= 3; i++){
            int inputNum = Integer.parseInt(userInput.substring(i,i+1));
            String colorGuess = ColorFormat.numberToColor(inputNum);
            userGuess.add(colorGuess);
        }
        CodePin guessPin1 = new CodePin(userGuess.get(0));
        CodePin guessPin2 = new CodePin(userGuess.get(1));
        CodePin guessPin3 = new CodePin(userGuess.get(2));
        CodePin guessPin4 = new CodePin(userGuess.get(3));
        Guess guess = new Guess(guessPin1, guessPin2, guessPin3, guessPin4);
        guessList.add(guess);
        return new Marks(guess, answer);
    }

     public static Marks processHypotheticalInput(String userInput, String hypotheticalAnswer) {
        Answer hypotheticalAns = new Answer(hypotheticalAnswer);
        ArrayList<String> userGuess = new ArrayList<>();
        for(int i = 0; i<= 3; i++){
            int inputNum = Integer.parseInt(userInput.substring(i,i+1));
            String colorGuess = ColorFormat.numberToColor(inputNum);
            userGuess.add(colorGuess);
        }
        CodePin guessPin1 = new CodePin(userGuess.get(0));
        CodePin guessPin2 = new CodePin(userGuess.get(1));
        CodePin guessPin3 = new CodePin(userGuess.get(2));
        CodePin guessPin4 = new CodePin(userGuess.get(3));
        Guess guess = new Guess(guessPin1, guessPin2, guessPin3, guessPin4);
        return new Marks(guess, hypotheticalAns);
    }

    public static ArrayList<String> generateCodes(){
        ArrayList <String> possibleCodes = new ArrayList<String>();
        for(int i = 1; i < 7; i ++){
            for(int j = 1;  j < 7; j ++){
                for(int k = 1;  k < 7; k ++){
                    for(int l = 1;  l < 7; l ++){
                        possibleCodes.add(""+ i + j + k + l);
                    } 
                } 
            } 
        }
        return possibleCodes;
    }

    public static Guess toGuess(int code) {
    String s = String.format("%04d", code); // ensures 4 digits
    return new Guess(
        new CodePin("" + s.charAt(0)),
        new CodePin("" + s.charAt(1)),
        new CodePin("" + s.charAt(2)),
        new CodePin("" + s.charAt(3))
        );
    }

    public static int scoreGuess(String guess, ArrayList<String> possible) {
    HashMap<String, Integer> partition = new HashMap<>();

        for (String possibleCode : possible) {
            Marks m = processHypotheticalInput(guess, possibleCode);
            String key = formatMarks(m.getMarks());          
            partition.put(key, partition.getOrDefault(key, 0) + 1);
        }

        int maxGroup = 0;
        for (int size : partition.values()) {
            if (size > maxGroup) {
                maxGroup = size;
            }
        }

        
        return maxGroup;
    }
}

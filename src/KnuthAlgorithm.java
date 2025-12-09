import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class KnuthAlgorithm {
    private static Scanner scan;
    private static ArrayList<Guess> guessList ;
    private static Answer answer;
    private static boolean gameWon;

    private static ArrayList <String> possibleCodes;
    private static ArrayList <String> allCodes;

    public KnuthAlgorithm() {
        
        answer = new Answer();
        System.out.println("secret: " + answer.getSequenceAsString() );
        gameWon = false;

        possibleCodes = generateCodes();
        allCodes = possibleCodes;

        guessList = new ArrayList<>();

    }

    public void runGame(){
       
        processTurn("1122");

        while(!gameWon){
            String nextGuess = chooseNextGuess();
            processTurn("" + nextGuess);
        }
    }

    public static void processTurn(String guess){
        System.out.println("guess: " + guess);
        Marks marks = processUserInput(guess);
        System.out.println(marks.getMarks());
        String markResult = formatMarks(marks.getMarks());
        int numblack = Integer.parseInt(markResult.substring(1));
        if(numblack == 4){
            System.out.println("Game won with " + guessList.size() + "guesses!");
        }
        else{
            for(String code : possibleCodes){
                checkIfPossible(marks.getMarks(), code);
            }
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
    public static boolean checkIfPossible(String marks, String possibleGuess){
        Marks possibleGuessMarks = processHypotheticalInput(possibleGuess);
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

     public static Marks processHypotheticalInput(String userInput) {
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
        return new Marks(guess, answer);
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
    // Map: markPattern → count
    HashMap<String, Integer> partition = new HashMap<>();

        for (String secret : possible) {
            Marks m = processUserInput(""+guess);
            String key = formatMarks(m.getMarks());          
            partition.put(key, partition.getOrDefault(key, 0) + 1);
        }

        // Worst case = the size of the LARGEST partition
        int maxGroup = 0;
        for (int size : partition.values()) {
            if (size > maxGroup) {
                maxGroup = size;
            }
        }

        // The "score" is the number of eliminated possibilities in the worst case
        int total = possible.size();
        return total - maxGroup;
    }
}

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.GraphicsText;

/**
 * Holds all game variables and methods for executing Donald Knuth's algorithm.
 * Not interactive- Displays the steps being played based on the algorithm's determination. 
 * @authors~~~
 * 
 */
public class KnuthAlgorithm {
    private final static int WINDOW_WIDTH = 300;
    private final static int WINDOW_HEIGHT = 600;
    private static CanvasWindow canvas;

    private static ArrayList<Guess> guessList;
    private static ArrayList<Marks> marksList;
    private static Visualizer visualizer;
    private static Answer answer;
    private static boolean gameWon;
    private static ArrayList <String> possibleCodes;
    private static ArrayList <String> allCodes;
    private static GraphicsGroup answerGraphic;
    private int frameCount = 0;
    private final int FRAMES_PER_STEP = 120;    

    /**
     * Constructs new KnuthAlgorithn object, initializes random answer, 
     * and initializes the possible codes and all codes lists.
     */
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

    /**
     * Runs the game by staggering the steps made, always starting with the guess "red, red, orange, orange"
     * Calls chooseNextGuess and processes the results repeatedly until the game is won. 
     */
    public void runGame(){
        visualize();
      
        canvas.animate(() -> {
            if (!gameWon) {
                frameCount++;

                if (frameCount < FRAMES_PER_STEP) {
                    return;   
                }
                frameCount = 0; 
                if (guessList.isEmpty()) {
                    processTurn("1122");
                } else {
                    String nextGuess = chooseNextGuess();
                    processTurn(nextGuess);
                }
                visualizer.update(guessList, marksList);
            }
        });
    }

    /**
     * Creates canvas window, adds the answer graphic to the screen and creates a Visualizer object. 
     */
    public static void visualize() {
        if (canvas != null) {
            canvas.closeWindow();
        }

        canvas = new CanvasWindow("Mastermind Game", WINDOW_WIDTH, WINDOW_HEIGHT);
        canvas.setBackground(Color.BLACK);

        answerGraphic.setCenter(WINDOW_WIDTH / 2 - 15, 60);
        canvas.add(answerGraphic);

        GraphicsText answerText = new GraphicsText("SECRET ANSWER");
        answerText.setFillColor(Color.white);
        answerText.setCenter(WINDOW_WIDTH / 2 - 15, 40);
        canvas.add(answerText);

        visualizer = new Visualizer(WINDOW_WIDTH, WINDOW_HEIGHT);
        visualizer.update(guessList, marksList);
        canvas.add(visualizer);

        canvas.draw();
    }

    /**
     * Takes the user's guess and gets the resulting marks. If all 4 are black, the game is won. 
     * Otherwise, the code is removed from both lists, and any now impossible codes are removed from possibleCodes. 
     */
    public static void processTurn(String guess){
        System.out.println("guess: " + guess);
        Marks marks = processUserInput(guess);
        System.out.println(marks.getMarks());
        marksList.add(marks);
        String markResult = formatMarks(marks.getMarks());
        int numblack = Integer.parseInt(markResult.substring(1));
        if (numblack == 4){
            GraphicsText winningText = new GraphicsText("Game won in "+ guessList.size() + " guesses!");
            winningText.setFillColor(Color.white);
            winningText.setFontSize(25);
            winningText.setCenter(WINDOW_WIDTH / 2, 550);
            canvas.add(winningText);
            System.out.println("Game won with " + guessList.size() + " guesses!");
            gameWon = true;
        } else {
            allCodes.remove(guess);
            possibleCodes.remove(guess);
            ArrayList<String> newList = new ArrayList<>();
            for (String code : possibleCodes) {
                if (checkIfPossible(marks, guess, code)) {
                    newList.add(code);
                }
            }
            possibleCodes = newList;
        }
    }

    /**
     * Creates a list of the best candidares by finding the maximum amount of remaining guesses after guessing each code. If there are ties, 
     * bestCandidates has multiple codes. Then, it finds the first code in best candidates which is still possible, and returns it as the 
     * next guess. If none of bestCandidates are still possible, the first item in it is returned. 
     */
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

    /**
     * Decides if based on the marks given for the given previous guess, it is possible that the possibleGuess posed could be the game answer which produced those marks.
     * @param actual - the actual marks which resulted
     * @param prevGuess - the guess which caused those marks
     * @param possibleGuess - the code being posed as a potential solution
     */
    public static boolean checkIfPossible(Marks actual, String prevGuess, String possibleGuess) {
    Marks hypothetical = processHypotheticalInput(prevGuess, possibleGuess);
        return actual.getNumBlack() == hypothetical.getNumBlack() && actual.getNumWhite() == hypothetical.getNumWhite();
    }

    public static String formatMarks(String phrase) {
        String numwhite = phrase.substring(33, 34);
        String numblack = phrase.substring(15,16);
        return numwhite + "" + numblack;
    }

    /**
     * Gives the marks correlating witbh the amount of codePins in the right location, and the amount
     * in the wrong location but the correct color. (This is the "codemaker's" perspective, and therefore
     * can only legally be called when a guess is actually made)
     */
    public static Marks processUserInput(String userInput) {
        ArrayList<String> userGuess = new ArrayList<>();
        for (int i = 0; i<= 3; i++) {
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

     /**
     * Given a hypothetical situation where hypotheticalAnswer is the game answer, gives the marks correlating witbh the amount of codePins in userInput 
     * in the right location, and the amount in the wrong location but the correct color. 
     * (This is the "guesser's perspective"-- they are not comparing to the actual answer, because they don't know it, but to a possible next guess they are considering 
     * whether it could or could not be the answer)
     */
    public static Marks processHypotheticalInput(String userInput, String hypotheticalAnswer) {
        Answer hypotheticalAns = new Answer(hypotheticalAnswer);
        ArrayList<String> userGuess = new ArrayList<>();
        for (int i = 0; i<= 3; i++) {
            int inputNum = Integer.parseInt(userInput.substring(i, i+1));
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

    public static ArrayList<String> generateCodes() {
        ArrayList <String> possibleCodes = new ArrayList<String>();
        for (int i = 1; i < 7; i ++) {
            for (int j = 1;  j < 7; j ++) {
                for (int k = 1;  k < 7; k ++) {
                    for (int l = 1;  l < 7; l ++) {
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

    /**
     * For a guess, and for every remaining possible guess in the list, adds to a map which has a key with the num black pins 
     * and num white pins and the amount of possible guesses that resulted in that key. Then, returns the maximum value as maxGroup. 
     * Thus, finding for given guess, what the maximum amount of remaining codes will be after guessking. 
     * (For every potential marks guess could return, which marks leave the greatest amount of codes in possible? These marks are worst case,
     * so return the amount of potential guesses which would produce them--the worst case remaining guesses for given guess).
     */
    public static int scoreGuess(String guess, ArrayList<String> possible) {
        HashMap<String, Integer> partition = new HashMap<>();

        for (String possibleCode : possible) {
            Marks m = processHypotheticalInput(guess, possibleCode);
            String key = m.getNumBlack() + "," + m.getNumWhite();         
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

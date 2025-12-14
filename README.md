# algorithms-fall2025-final
By: Avi, Courtney, Elyse, and Nora

To run code: Run main in Main
No other libraries should be required.

This project is an implementation of Donald Knuth's algorithm for solving the game Mastermind, with a visual element included. The actual algorithm itself is contained in the KnuthAlgorithm file, with the visual element making up the rest of the program files (except for main).

The Knuth Mastermind Algorithm uses the Minimax technique as follows:
    * First, a set is created of all possible codes which could be the solution. We keep track of both allCodes and possibleCodes distinctly

    * Second, the first guess is made. It is always "red, red, orange, orange" (1122 in number form). The algorithm would work with any initial guess following the same pattern, ie.: 4411 etc. 

    * Third, marks are caclulated resulting from that guess. If it is 4 black marks, the game is won. Otherwise, all remaining codes which could not be the solution and have caused those marks to be produced by the previous guess are removed from possibleCodes. 

    * Fourth, the minimax technique is used to form the next best guess. Each code in allCodes recieves a score, which is the maximum amount of possible codes that could remain after guessing it. (process detailed in scoreGuess javadoc). chooseNextGuess collects the codes with the lowest score. In the case of ties, it attempts to choose one which is still in possibleCodes. If none are, it just returns the first chronologically. 

    * Steps 3 and 4 are repeated until the game is won, which is always in 5 guesses or less. 

Source:
[The Computer As Mastermind](https://www.cs.uni.edu/~wallingf/teaching/cs3530/resources/knuth-mastermind.pdf) by Donald Knuth

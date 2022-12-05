import java.util.Scanner;
public class Runner {
    public static void main(String[] args) {
        System.out.println("welcome to 2048!");
        // Creates scanner
        Scanner scan = new Scanner(System.in);
        String userInput;
        // declares all acceptable inputs to then check against
        String[] acceptableInputs = {"up", "down", "left", "right", "undo"};
        String alt = "wsad";
        // Instantiates a new Board object
        Board gameBoard = new Board();
        // Keeps track of whether the last move was undo because there is only one previous game state slot
        boolean lastMoveWasUndo = false;
        do {
            // Prompts the user for input
            System.out.print("enter a direction(\"up\", \"down\", \"left\",\"right\", \"w\", \"a\", \"s\", \"d\", \"undo\" to undo, or \"quit\" to quit) ");
            // Stores input
            userInput = scan.nextLine().toLowerCase();
            // Keeps track of whether the input is acceptable
            boolean goodArgs = false;
            // loops through each index of the array and sets goodArgs to true if the input is in the list, exits the loop if the input is in the list
            for (int i = 0; i < acceptableInputs.length && !goodArgs; i++) if (acceptableInputs[i].equals(userInput)) goodArgs = true;
            // Also sets goodArgs to true if the input is one letter long and is either w, a, s, or d.
            if (alt.contains(userInput) && userInput.length() == 1) {
                goodArgs = true;
                // sets user input to a directional word for compatibility with logic.
                userInput = acceptableInputs[alt.indexOf(userInput)];
            }
            // Runs the loop again when the move cannot be handled
            if (!goodArgs) System.out.println("that's not a valid move!");
            // if the user chose to undo, check if the last move was the same and set lastMoveWasUndo to true
            else if (userInput.equals("undo")) {
                // deny undo-ing twice in a row because the game only stores one previous state
                if (lastMoveWasUndo) System.out.println("cannot undo twice in a row.");
                else {
                    lastMoveWasUndo = true;
                    gameBoard.move(userInput);
                }
            }
            // if the user inputted a directional move, set lastMoveWasUndo to false and call the move method on the gameBoard object
            else {
                lastMoveWasUndo = false;
                gameBoard.move(userInput);
            }
            // quits the game when the user enters "quit" or loses the game
        } while (!userInput.equals("quit") && !gameBoard.gameLost());
        scan.close();
    }
}

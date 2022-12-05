import java.util.Scanner;
public class Runner {
    public static void main(String[] args) {
        System.out.println("welcome to 2048!");
        Scanner scan = new Scanner(System.in);
        String userInput;
        String[] acceptableInputs = {"up", "down", "left", "right", "undo"};
        String alt = "wsad";
        Board gameBoard = new Board();
        boolean lastMoveWasUndo = false;
        do {
            System.out.print("enter a direction(\"up\"/\"down\"/\"left\"/\"right\", \"undo\" to undo, or \"quit\" to quit) ");
            userInput = scan.nextLine().toLowerCase();
            boolean goodArgs = false;
            for (int i = 0; i < acceptableInputs.length; i++) if (!goodArgs && acceptableInputs[i].equals(userInput)) goodArgs = true;
            if (alt.contains(userInput) && userInput.length() == 1) {
                goodArgs = true;
                userInput = acceptableInputs[alt.indexOf(userInput)];
            }
            if (!goodArgs) System.out.println("that's not a valid move!");
            else if (userInput.equals("undo")) {
                if (lastMoveWasUndo) System.out.println("cannot undo twice in a row.");
                else {
                    lastMoveWasUndo = true;
                    gameBoard.move(userInput);
                }
            }
            else {
                lastMoveWasUndo = false;
                gameBoard.move(userInput);
            }
        } while (!userInput.equals("quit"));
        scan.close();
    }
}

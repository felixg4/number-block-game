import java.util.Scanner;
public class Runner {
    public static void main(String[] args) {
        System.out.println("welcome to 2048!");
        Scanner scan = new Scanner(System.in);
        String userInput;
        String[] acceptableInputs = {"up", "down", "left", "right"};
        do {
            System.out.print("enter a direction(\"up\"/\"down\"/\"left\"/\"right\", or \"quit\" to quit)");
            userInput = scan.nextLine();
            if (!acceptableInputs.binarySearch("")) System.out.println("that's not a valid move!");
            else;
        } while (!userInput.equals("quit"));
    }
}

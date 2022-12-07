/**
 * This class represents a 2048 game board
 */
public class Board {
    /** An integer matrix that stores all game blocks*/
    private int[][] boardArray = new int[4][4];
    /** An integer matrix that is supposed to store the previous game board state but it didn't want to work */
    private int[][] prevBoardArray = boardArray;
    /** An integer that stores the value of the longest number created, mainly for spacing purposes */
    private int longest = 1;
    /** A boolean that stores whether the game has been lost game state */
    private boolean lost = false;

    /**
     * Default constructor initializes game board randomly and prints it out
     */
    public Board() {
        // creates a maximum of two blocks to start with
        for (int i = 0; i < (int) (Math.random() * 2) + 1; i++) {
            // int variable will store the random location chosen to place a block in
            int randLoc;
            do {
                // the location is a number from 0 to 15, inclusive
                randLoc = (int) (Math.random() * 16);
                // sets a random square to either 2 or 4
                setSquare(randLoc, (int) Math.pow(2, (int) (Math.random() * 2)) + 1);
            } while (getSquare(randLoc) < 2); // the location is re-selected if the location on the board is occupied
        }
        System.out.print(this);
    }

    /**
     * Four parameter constructor sets the game board to defined integers at defined positions (mainly for testing purposes) and prints out the game board
     * @param startingNum1 is one of the integers to place on the board
     * @param startingNum2 is the other integer that will be placed on the board
     * @param startingNum1Loc is the location (a number between 0 and 15, inclusive) where startingNum1 is placed on the board
     * @param startingNum2Loc is the location (a number between 0 and 15, inclusive) where startingNum2 is placed on the board
     */
    public Board(int startingNum1, int startingNum2, int startingNum1Loc, int startingNum2Loc) {
        setSquare(startingNum1Loc, startingNum2Loc);
        setSquare(startingNum2Loc, startingNum2);
        System.out.print(this);
    }
    /**
     * Returns the value of an index of the boardArray
     * @param loc an integer between 0 and 15, inclusive, that represents an index of the boardArray if it was 1-dimensional
     * @return an integer that exists at the specified location
     */
    private int getSquare(int loc) {
        return boardArray[loc / 4][loc % 4];
    }

    /**
     * Sets a square to a new value
     * @param loc is an integer that represents the location of the index to modify the value of, an integer from 0 to 15, inclusive
     * @param newVal is an integer that the location will be set to
     */
    private void setSquare(int loc, int newVal) {
        boardArray[loc / 4][loc % 4] = newVal;
    }

    /**
     * Returns a String representation of the boardArray
     * @return a String containing every block in the boardArray at its respective position
     */
    public String toString() {
        // declares the String variable that will store everything
        String res = "";
        // calculates the length of the longest number plus two spaces around it for the columns to be even
		int longestLen = (int) Math.ceil(Math.log10(longest)) + 2;
        // stores the foreground and background color combinations to use to style blocks depending on their value
        String[] textCodes = {
            "\033[1;38;2;119;110;101;48;2;237;224;200m",
            "\033[1;38;2;249;246;242;48;2;242;177;121m",
            "\033[1;38;2;249;246;242;48;2;245;149;99m",
            "\033[1;38;2;249;246;242;48;2;246;124;95m"
        };
        // loops through each row
        for (int row = 0; row < 4; row++) {
            // surrounds each block with a pipe on the left
            res += "|";
            // loops through each column
            for (int col = 0; col < 4; col++) {
                // stores the value at the index currently reached by the nested loop
                int val = boardArray[row][col];
                // calculates the length of the value currently reached by the loop and caps the minimum length at 1 if the value is 0
                int valLength = (int) Math.max(Math.ceil(Math.log10(val)), 1);
                // calculates the amount of extra spaces to be added so that the block length is uniform
                int extraSpacing = longestLen - valLength;
                // if the value is not 0, style the whole block with color depending on how large it is, this basically takes the index at log2(val)
                if (val > 0) res += textCodes[(int) (Math.log10(val)/Math.log10(2) - 1) % textCodes.length];
                // adds the extra spacing on the left
                for (int i = 0; i < extraSpacing / 2; i++) res += " ";
                // if the value is greater than 0, show it in the block, otherwise the block will be empty
                if (val > 0) res += val;
                else res += " ";
                // add the extra spacing on the right
                for (int i = 0; i < extraSpacing / 2 + extraSpacing % 2; i++) res += " ";
                // add a blank text style to stop the styling from spreading to the next block over
                res += "\033[0m";
                // add a pipe on the right to separate this block from the next one
                res += "|";
            }
            // skip to the next line to add dashes
            res += "\n";
            // add the correct amount of dashes: there are four blocks in a row and each has the length of longestLen and its pipe
            for (int i = 0; i <= 4 * (longestLen + 1); i++) res += "-";
            // skip to the next line to add numbers again
            res += "\n";
        }
        return res;
    }

    /**
     * Executes the main logic for performing a move on the game board, checks if a player has lost
     * @param direction can be "up", "down", "left", "right", or "undo" but undo doesn't work.
     */
    public void move(String direction) {
        if (direction.equals("up")) {
            // sets the prevBoardArray to boardArray for undo purposes except it doesn't work
            prevBoardArray = boardArray;
            // loops through each column
            for (int col = 0; col < 4; col++) {
                // shifts the column left to get rid of empty cells and gets teh integers next to each other for combination comparison
                int[] newCol = shiftLeft(getColumn(col));
                // sets each cell in the column of the gameBoard to what it should be after the shift.
                for (int row = 0; row < 4; row++) boardArray[row][col] = newCol[row];
                // loops through each row except for the one where there is no block above
                for (int row = 1; row < 4; row++) {
                    // stores the value the nested for loop has reached
                    int val = boardArray[row][col];
                    // checks to see if the cell is empty, then checks whether an adjacent block is equal the current block 
                    if (val > 0 && boardArray[row - 1][col] == val) {
                        // sets the current block to 0
                        boardArray[row][col] = 0;
                        // "combines" the blocks by multiplying the value of the above block by 2
                        boardArray[row - 1][col] = val * 2;
                        // keeps track of the longest number in the board
                        if (val * 2 > longest) longest = val * 2;
                    }
                }
            }
        } else if (direction.equals("down")) {
            prevBoardArray = boardArray;
            for (int col = 0; col < 4; col++) {
                int[] newCol = shiftRight(getColumn(col));
                for (int row = 0; row < 4; row++) boardArray[row][col] = newCol[row];
                for (int row = 0; row < 3; row++) {
                    int val = boardArray[row][col];
                    if (val > 0 && boardArray[row + 1][col] == val) {
                        boardArray[row][col] = 0;
                        boardArray[row + 1][col] *= 2;
                        if (val * 2 > longest) longest = val * 2;
                    }
                }
            }
        }
        else if (direction.equals("left")) {
            prevBoardArray = boardArray;
            for (int row = 0; row < 4; row++) {
                boardArray[row] = shiftLeft(boardArray[row]);
                for (int col = 1; col < 4; col++) {
                    int val = boardArray[row][col];
                    if (val > 0 && boardArray[row][col - 1] == val) {
                        boardArray[row][col] = 0;
                        boardArray[row][col - 1] *= 2;
                        if (val * 2 > longest) longest = val * 2;
                    }
                }
            }
        } else if (direction.equals("right")) {
            prevBoardArray = boardArray;
            for (int row = 0; row < 4; row++) {
                boardArray[row] = shiftRight(boardArray[row]);
                for (int col = 2; col >= 0; col--) {
                    int val = boardArray[row][col];
                    if (val > 0 && boardArray[row][col + 1] == val) {
                        boardArray[row][col] = 0;
                        boardArray[row][col + 1] *= 2;
                        if (val * 2 > longest) longest = val * 2;
                    }
                }
            }
        } else {
            // 
            boardArray = prevBoardArray;
            System.out.println(this);
            return;
        }
        boolean canMatch = false;
        // checks if any match is possible in the current game board
        for (int row = 0; row < 4 && !canMatch; row++) {
            for (int col = 0; col < 4 && !canMatch; col++) {
                if (!canMatch) {
                    int val = boardArray[row][col];
                    if ((row < 3 && val == boardArray[row + 1][col]) || (row > 0 && val == boardArray[row - 1][col])) canMatch = true;
                    if ((col < 3 && val == boardArray[row][col + 1]) || (col > 0 && val == boardArray[row][col - 1])) canMatch = true;
                }
            }
        }
        // creates an array of empty cells
		int[] emptySpots = new int[16];
        int j = 0;
		for (int i = 0; i < 16; i++) if (getSquare(i) == 0) emptySpots[j++] = i;
        // the player loses if there are no empty cells and no matches can be made
		if (!canMatch && j == 0) {
			System.out.println("you lost! the biggest number you created was " + longest);
            lost = true;
			return;
		}
        // place a random block in an empty cell
        setSquare(emptySpots[(int) (Math.random() * j)], (int) Math.pow(2, (int) (Math.random() * 2) + 1));
        System.out.println(this);
    }

    /**
     * Gets a given column of the boardArray as an integer array
     * @param column is the number of the column to get, an integer from 0 to 3, inclusive
     * @return the requested column as an integer array
     */
    private int[] getColumn(int column) {
        int[] newArr = new int[4];
        // loops through each row and appends the item in at a given index in each row
        for (int i = 0; i < 4; i++) newArr[i] = boardArray[i][column];
        return newArr;
    }

    /**
     * 'Shifts' an array to the left, removing all zeroes in between numbers and moving all nonzero integers to the left
     * @param arr is the array to be manipulated
     * @return an array with all nonzero integers next to each other on the left side
     */
    private int[] shiftLeft(int[] arr) {
        int[] newArr = new int[4];
        int j = 0;
        for (int i = 0; i < 4; i++) if (arr[i] != 0) newArr[j++] = arr[i];
        return newArr;
    }

    /**
     * Shifts' an array to the right, removing all zeroes in between numbers and moving all nonzero integers to the right
     * @param arr is the array to be manipulated
     * @return an array with all nonzero integers next to each other on the left side
     */
    private int[] shiftRight(int[] arr) {
        int[] newArr = new int[4];
        int j = 3;
        for (int i = 3; i >= 0; i--) if (arr[i] > 0) newArr[j--] = arr[i];
        return newArr;
    }

    /**
     * Returns whether the game has been lost
     * @return the value of the lost variable, which represents whether or not the game has been lost
     */
    public boolean gameLost() {
        return lost;
    }
}
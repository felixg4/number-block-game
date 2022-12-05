public class Board {
    private int[][] boardArray = new int[4][4];
    private int[][] prevBoardArray = boardArray;
    private int longest = 1;
    private boolean lost = false;
    public Board() {
        for (int i = 0; i < (int) (Math.random() * 2) + 1; i++) {
            int randLoc;
            do {
                randLoc = (int) (Math.random() * 16);
                setSquare(randLoc, (int) Math.pow(2, (int) Math.random() * 2) + 1);
            } while (getSquare(randLoc) < 2);
        }
        System.out.print(this);
    }
    public Board(int startingNum1, int startingNum2, int startingNum1Loc, int startingNum2Loc) {
        setSquare(startingNum1Loc, startingNum2Loc);
        setSquare(startingNum2Loc, startingNum2);
        System.out.print(this);
    }
    // make a 0 parameter method
    private int getSquare(int loc) {
        return boardArray[loc / 4][loc % 4];
    }
    private void setSquare(int x, int y, int newVal) {
        boardArray[x][y] = newVal;
    }
    private void setSquare(int loc, int newVal) {
        setSquare(loc / 4, loc % 4, newVal);
    }
    public String toString() {
        String res = "";
		int longestLen = (int) Math.ceil(Math.log10(longest)) + 2;
        String[] textCodes = {
            "\033[1;38;2;119;110;101;48;2;237;224;200m",
            "\033[1;38;2;249;246;242;48;2;242;177;121m",
            "\033[1;38;2;249;246;242;48;2;245;149;99m",
            "\033[1;38;2;249;246;242;48;2;246;124;95m"
        };
        for (int row = 0; row < 4; row++) {
            res += "|";
            for (int col = 0; col < 4; col++) {
                int val = boardArray[row][col];
                int valLength = (int) Math.max(Math.ceil(Math.log10(val)), 1);
                int extraSpacing = longestLen - valLength;
                if (val > 0) res += textCodes[(int) (Math.log10(val)/Math.log10(2) - 1) % textCodes.length];
                for (int i = 0; i < extraSpacing / 2; i++) res += " ";
                if (val > 0) res += val;
                else res += " ";
                for (int i = 0; i < extraSpacing / 2 + extraSpacing % 2; i++) res += " ";
                res += "\033[0m";
                res += "|";
            }
            res += "\n";
            for (int i = 0; i <= 4 * (longestLen + 1); i++) res += "-";
            res += "\n";
        }
        return res;
    }
    public void move(String direction) {
        if (direction.equals("up")) {
            prevBoardArray = boardArray;
            for (int col = 0; col < 4; col++) {
                int[] newCol = shiftLeft(getColumn(col));
                for (int row = 0; row < 4; row++) boardArray[row][col] = newCol[row];
                for (int row = 1; row < 4; row++) {
                    int val = boardArray[row][col];
                    if (val > 0 && boardArray[row - 1][col] == val) {
                        boardArray[row][col] = 0;
                        boardArray[row - 1][col] = val * 2;
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
            boardArray = prevBoardArray;
            System.out.println(this);
            return;
        }
        boolean canMatch = false;
        for (int row = 0; row < 4 && !canMatch; row++) {
            for (int col = 0; col < 4 && !canMatch; col++) {
                if (!canMatch) {
                    int val = boardArray[row][col];
                    if ((row < 3 && val == boardArray[row + 1][col]) || (row > 0 && val == boardArray[row - 1][col])) canMatch = true;
                    if ((col < 3 && val == boardArray[row][col + 1]) || (col > 0 && val == boardArray[row][col - 1])) canMatch = true;
                }
            }
        }
		int[] emptySpots = new int[16];
        int j = 0;
		for (int i = 0; i < 16; i++) if (getSquare(i) == 0) emptySpots[j++] = i;
		if (!canMatch && j == 0) {
			System.out.println("you lost! the biggest number you created was " + longest);
            lost = true;
			return;
		}
        setSquare(emptySpots[(int) (Math.random() * j)], (int) Math.pow(2, (int) (Math.random() * 2) + 1));
        System.out.println(this);
    }
    private int[] getColumn(int column) {
        int[] newArr = new int[4];
        for (int i = 0; i < 4; i++) newArr[i] = boardArray[i][column];
        return newArr;
    }
    private int[] shiftLeft(int[] arr) {
        int[] newArr = new int[4];
        int j = 0;
        for (int i = 0; i < 4; i++) if (arr[i] != 0) newArr[j++] = arr[i];
        return newArr;
    }
    private int[] shiftRight(int[] arr) {
        int[] newArr = new int[4];
        int j = 3;
        for (int i = 3; i >= 0; i--) if (arr[i] > 0) newArr[j--] = arr[i];
        return newArr;
    }
    public boolean gameLost() {
        return lost;
    }
}
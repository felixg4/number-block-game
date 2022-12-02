public class Board {
    private int[][] boardArray = new int[4][4];
    private int longest = 1;
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
		int longestLen = (int) Math.ceil(Math.log10(longest));
        for (int row = 0; row < 4; row++) {
            res += "| ";
            for (int col = 0; col < 4; col++) {
                int val = boardArray[row][col];
                int valLength = (int) Math.ceil(Math.log10(val));
                int extraSpacing = longestLen - valLength;
                for (int i = 0; i < extraSpacing / 2 + extraSpacing % 2; i++) res += " ";
                res += val;
                for (int i = 0; i < extraSpacing / 2 + extraSpacing % 2; i++) res += " ";
                res += " | ";
            }
            res += "\n";
            for (int i = 0; i < 4*(longestLen + 4); i++) res += "-";
            res += "\n";
        }
        return res;
    }
    public void move(String direction) {
		int[] emptySpots = new int[16];
		int j = 0;
		for (int i = 0; i < 16; i++) {
			if (getSquare(i) < 2) emptySpots[j++] = i;
		}
        // fix this logic
        boolean canMove = false;
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                // can a block be combined with a block next to it?
            }
        }
		if (j == 0) {
			System.out.println("you lost! the biggest number you created was " + longest);
			return;
		}
        // place new blocks here
        if (direction.equals("up")) {
            for (int col = 0; col < 4; col++) {
                int[] newCol = shiftLeft(getColumn(col), true);
                for (int row = 0; row < 4; row++) boardArray[row][col] = newCol[row];
                for (int row = 3; row > 0; row--) {
                    if (boardArray[row][col] > 0 && boardArray[row - 1][col] == boardArray[row][col]) {
                        boardArray[row][col] = 0;
                        boardArray[row - 1][col] *= 2;
                        if (boardArray[row - 1][col] > longest) longest = boardArray[row - 1][col];
                        System.out.println(this);
                        return;
                    }
                }
                newCol = shiftLeft(getColumn(col), true);
                for (int row = 0; row < 4; row++) boardArray[row][col] = newCol[row];
            }
        } else if (direction.equals("down")) {
            for (int col = 0; col < 4; col++) {
                int[] newCol = shiftLeft(getColumn(col), false);
                for (int row = 0; row < 4; row++) boardArray[row][col] = newCol[row];
                for (int row = 0; row < 3; row++) {
                    if (boardArray[row][col] > 0 && boardArray[row + 1][col] == boardArray[row][col]) {
                        boardArray[row][col] = 0;
                        boardArray[row + 1][col] *= 2;
                        if (boardArray[row + 1][col] > longest) longest = boardArray[row + 1][col];
                        System.out.println(this);
                        return;
                    }
                }
                newCol = shiftLeft(getColumn(col), false);
                for (int row = 0; row < 4; row++) boardArray[row][col] = newCol[row];
            }
        } else if (direction.equals("left")) {
            for (int row = 0; row < 4; row++) {
                boardArray[row] = shiftLeft(boardArray[row], true);
                for (int col = 1; col < 4; col++) {
                    int val = boardArray[row][col];
                    if ( boardArray[row][col] > 0 && boardArray[row][col - 1] == val) {
                        boardArray[row][col] = 0;
                        boardArray[row][col - 1] *= 2;
                        if (boardArray[row][col - 1] > longest) longest = boardArray[row][col - 1];
                        System.out.println(this);
                        return;
                    }
                }
                boardArray[row] = shiftLeft(boardArray[row], true);
            }
        } else {
            for (int row = 0; row < 4; row++) {
                boardArray[row] = shiftLeft(boardArray[row], false);
                for (int col = 2; col >= 0; col--) {
                    int val = boardArray[row][col];
                    if (boardArray[row][col] > 0 && boardArray[row][col + 1] == val) {
                        boardArray[row][col] = 0;
                        boardArray[row][col + 1] *= 2;
                        if (boardArray[row][col + 1] > longest) longest = boardArray[row][col + 1];
                        System.out.println(this);
                        return;
                    }
                }
                boardArray[row] = shiftLeft(boardArray[row], false);
            }
        }
        System.out.println(this);
    }
    private int[] getColumn(int column) {
        int[] newArr = new int[4];
        for (int i = 0; i < 4; i++) newArr[i] = boardArray[i][column];
        return newArr;
    }
    private int[] shiftLeft(int[] arr, boolean moveRight) {
        int[] newArr = new int[4];
        int j = 0;
        for (int i = 0; i < 4; i++) {
            if (arr[i] != 0) {
                newArr[j] = arr[i];
                j++;
            }
        }
        return moveRight ? newArr : reverse(newArr);
    }
    private int[] reverse(int[] arr) {
        int[] newArr = new int[4];
        for (int i = 0; i < 4; i++) newArr[i] = arr[3 - i];
        return newArr;
    }
}
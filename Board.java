public class Board {
    private int[][] boardArray;
    public Board() {
        for (int i = 0; i < (int) (Math.random() * 2) + 1; i++) {
            int randLoc;
            do {
                randLoc = (int) (Math.random() * 16);
                setSquare(randLoc, (int) Math.pow(2, (int) Math.random() * 2) + 1);
            } while (getSquare(randLoc) < 2);
        }
    }
    private int getSquare(int x, int y) {
        return boardArray[x][y];
    }
    private int getSquare(int loc) {
        return getSquare(loc / 4, loc % 4);
    }
    private void setSquare(int x, int y, int newVal) {
        boardArray[x][y] = newVal;
    }
    private void setSquare(int loc, int newVal) {
        setSquare(loc / 4, loc % 4, newVal);
    }
    private int checkSquare(String direction, int currentSquareX, int currentSquareY) {
        if (direction.equals("left")) {
            if (currentSquareX == 0) return 0;
            return getSquare(currentSquareX - 1, currentSquareY);
        }
        if (direction.equals("right")) {
            if (currentSquareX == 3) return 0;
            return getSquare(currentSquareX + 1, currentSquareY);
        }
        if (direction.equals("up")) {
            if (currentSquareY == 0) return 0;
            return getSquare(currentSquareX, currentSquareY - 1);
        }
        if (direction.equals("down")) {
            if (currentSquareY == 3) return 0;
            return getSquare(currentSquareX, currentSquareY + 1);
        }
        else {
            System.out.println("you screwed up");
            return 0;
        }
    }
    private int checkSquare(String direction, int currentSquareLoc) {
        return checkSquare(direction, currentSquareLoc / 4, currentSquareLoc % 4);
    }
    public String toString() {
        int longest = 0;
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                if (boardArray[row][col] > longest) longest = boardArray[row][col];
            }
        }
        int spacing = (int) Math.ceil(Math.log10(longest));
        String res = "";
        for (int row = 0; row < 4; row++) {
            res += "|";
            for (int col = 0; col < 4; col++) {
                int val = boardArray[row][col];
                int valLength = (int) Math.ceil(Math.log10(val));
                int extraSpacing = spacing - valLength;
                 for (int i = 0; i < extraSpacing / 2; i++) {
                     res += " ";
                 }
                 res += val;
                for (int i = 0; i < extraSpacing / 2 + extraSpacing % 2; i++) {
                    res += " ";
                }
                res += " |";
            }
            res += "\n";
            for (int i = 0; i < 4*(spacing + 3); i++) {
                res += "-";
            }
            res += "\n";
        }
        return res;
    }
}

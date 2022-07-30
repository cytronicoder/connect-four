public class Position {
    public static int WIDTH = 7;  // Width of the board
    public static int HEIGHT = 6; // Height of the board

    private int board[][] = new int[WIDTH][HEIGHT]; // 2D array of the board
    private int height[] = new int[WIDTH]; // 1D array of the height of each column
    private int moves = 0; // Number of moves made so far

    /** Constructor */
    public Position() {
        // Initialize the board to empty
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                board[i][j] = 0;
            }
        }
        // Initialize the height of each column to 0
        for (int i = 0; i < WIDTH; i++) {
            height[i] = 0;
        }
    }

    /** Copy constructor */
    public Position(Position other) {
        // Copy the board
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                board[i][j] = other.board[i][j];
            }
        }
        // Copy the height
        for (int i = 0; i < WIDTH; i++) {
            height[i] = other.height[i];
        }
        // Copy the number of moves
        moves = other.moves;
    }

    /**
     * Check if the current player can make a move at the given column.
     * @param column The column to check.
     * @return True if the current player can make a move at the given column.
     */
    public boolean canMove(int column) {
        return height[column] < HEIGHT;
    }

    /**
     * Make a move at the given column.
     * @param column The column to make the move at.
     * @return True if the move was successful, false otherwise.
     */
    public boolean move(int column) {
        if (canMove(column)) {
            board[column][height[column]] = moves % 2 + 1;
            height[column]++;
            moves++;
            return true;
        }
        return false;
    }

    /**
     * Make a move based on the given move string.
     * The move string is a sequence of characters '1' to '7' representing the column to move to.
     * @param move The move string to make.
     */
    public void move(String move) {
        for (int i = 0; i < move.length(); i++) {
            int column = move.charAt(i) - '1';
            move(column);
        }
    }
    
    /**
     * Check if there is a winning move for the current player in the given column.
     * @param column The column to check.
     * @return True if there is a winning move for the current player in the given column.
     */
    public boolean canWin(int column) {
        int currentPlayer = moves % 2 + 1;
        
        if (height[column] >= 3
            && board[column][height[column] - 1] == currentPlayer
            && board[column][height[column] - 2] == currentPlayer
            && board[column][height[column] - 3] == currentPlayer) {
            return false;
        }

        for (int dy = -1; dy <= 1; dy++) {
            int nb = 0;
            for (int dx = -1; dx <= 1; dx += 2) {
                for (int x = column + dx, y = height[column] + dx * dy;
                     x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT && 
                     board[x][y] == currentPlayer; nb++) {
                    x += dx;
                    y += dx * dy;
                }
                if (nb >= 3) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Check if the game has been won.
     * @return True if the game has been won.
     */
    public boolean isWon() {
        for (int i = 0; i < WIDTH; i++) {
            if (canWin(i)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the number of moves made so far.
     * @return The number of moves made so far.
     */
    public int getMoves() {
        return moves;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                sb.append("[ " + board[j][i] + " ]");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}

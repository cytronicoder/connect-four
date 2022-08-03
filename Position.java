public class Position {
    public static int WIDTH = 7;  // Width of the board
    public static int HEIGHT = 6; // Height of the board

    private int board[][] = new int[WIDTH][HEIGHT]; // 2D array of the board
    private int height[] = new int[WIDTH]; // 1D array of the height of each column
    private int moves; // Number of moves made so far

    /** Constructors */
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

        // Initialize the moves to 0
        moves = 0;
    }

    public Position(Position pos) {
        // Copy the board
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                board[i][j] = pos.board[i][j];
            }
        }

        // Copy the height
        for (int i = 0; i < WIDTH; i++) {
            height[i] = pos.height[i];
        }

        // Copy the moves
        moves = pos.moves;
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
     */
    public void move(int column) {
        board[column][height[column]] = moves % 2 + 1;
        height[column]++;
        moves++;
    }

    /**
     * Make a move based on the given move string.
     * The move string is a sequence of characters '1' to '7' representing the column to move to.
     * @param move The move string to make.
     * @return The number of moves made.
     */
    public int move(String move) {
        for (int i = 0; i < move.length(); i++) {
            int column = move.charAt(i) - '1';
            if(column < 0 || column >= Position.WIDTH || !canMove(column) || isWinningMove(column)) return i;
            move(column);
        }
        return move.length();
    }
    
    /**
     * Check if there is a winning move for the current player in the given column.
     * @param column The column to check.
     * @return True if there is a winning move for the current player in the given column.
     */
    public boolean isWinningMove(int column) {
        int currentPlayer = moves % 2 + 1;
        
        if(height[column] >= 3 
            && board[column][height[column] - 1] == currentPlayer 
            && board[column][height[column] - 2] == currentPlayer 
            && board[column][height[column] - 3] == currentPlayer) {
          return true;
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
                if(nb >= 3) return true;
            }
        }
        return false;
    }

    /** Check if a player has won the game.
     * @param player The player to check.
     * @return True if the player has won the game.
     */
    public boolean hasWon(int player) {
        // horizontal
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH - 3; j++) {
                if (board[j][i] == player && board[j + 1][i] == player && board[j + 2][i] == player && board[j + 3][i] == player) {
                    return true;
                }
            }
        }

        // vertical
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT - 3; j++) {
                if (board[i][j] == player && board[i][j + 1] == player && board[i][j + 2] == player && board[i][j + 3] == player) {
                    return true;
                }
            }
        }

        // diagonal
        for (int i = 3; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT - 3; j++) {
                if (board[i][j] == player && board[i - 1][j + 1] == player && board[i - 2][j + 2] == player && board[i - 3][j + 3] == player) {
                    return true;
                }
            }
        }

        for (int i = 3; i < WIDTH; i++) {
            for (int j = 3; j < HEIGHT; j++) {
                if (board[i][j] == player && board[i - 1][j - 1] == player && board[i - 2][j - 2] == player && board[i - 3][j - 3] == player) {
                    return true;
                }
            }
        }

        return false;
    }

    /** Check if the game is over.
     * @return True if the game is over.
     */
    public boolean isGameOver() {
        return moves == WIDTH * HEIGHT || hasWon(1) || hasWon(2);
    }

    /** Get winner of the game.
     * @return The winner of the game.
     */
    public int getWinner() {
        if (hasWon(1)) {
            return 1;
        } else if (hasWon(2)) {
            return 2;
        } else {
            return 0;
        }
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
        // print it from the bottom up
        StringBuilder sb = new StringBuilder();
        for (int i = HEIGHT - 1; i >= 0; i--) {
            sb.append(i + 1);
            sb.append(" | ");
            for (int j = 0; j < WIDTH; j++) {
                sb.append(board[j][i] == 0 ? "[ . ]" : board[j][i] == 1 ? "[ X ]" : "[ O ]");
            }
            sb.append("\n");
        }
        sb.append("    ");
        for (int i = 0; i < WIDTH; i++) {
            sb.append("  " + (i + 1) + "  ");
        }
        sb.append("\n");
        return sb.toString();
    }
}

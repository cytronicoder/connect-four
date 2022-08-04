public class Position {
    public static final int WIDTH = 7;  // Width of the board
    public static final int HEIGHT = 6; // Height of the board
    public static final int MIN_SCORE = -(WIDTH * HEIGHT) / 2 + 3;
    public static final int MAX_SCORE = (WIDTH * HEIGHT + 1) / 2 - 3;
    

    private long current_position; // The current position of the board represented as a bitboard
    private long mask; // Bitmask for the current position
    private int moves; // Number of moves made so far

    /** Constructors */
    public Position() {
        // Initialize the board to empty
        current_position = 0;

        // Initialize the mask to empty
        mask = 0;

        // Initialize the moves to 0
        moves = 0;
    }

    public Position(Position pos) {
        // Copy the board
        current_position = pos.current_position;
        mask = pos.mask;
        moves = pos.moves;
    }

    /**
     * Check if the current player can make a move at the given column.
     * @param column The column to check.
     * @return True if the current player can make a move at the given column.
     */
    public boolean canMove(int column) {
        return (mask & top_mask(column)) == 0;
    }

    /**
     * Make a move at the given column.
     * @param column The column to make the move at.
     */
    public void move(int column) {
        current_position ^= mask;
        mask |= mask + bottom_mask(column);
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
        long pos = current_position; 
        pos |= (mask + bottom_mask(column)) & column_mask(column);
        return alignment(pos);
    }
    
    /** GETTERS */
    /**
     * Get the number of moves made so far.
     * @return The number of moves made so far.
     */
    public int getMoves() {
        return moves;
    }

    /** Get the key of the current position.
     * @return The key of the current position.
     */
    public long getKey() {
        return current_position + mask;
    }

    /** PRIVATE METHODS */
    /** Check if the given position is a winning position.
     * @param pos The position to check.
     * @return True if the given position is a winning position.
     */
    private static boolean alignment(long pos) {
        // horizontal 
        long m = pos & (pos >> (HEIGHT + 1));
        
        if((m & (m >> (2 * (HEIGHT + 1)))) != 0) return true;

        // diagonal 1
        m = pos & (pos >> HEIGHT);
        if((m & (m >> (2 * HEIGHT))) != 0) return true;

        // diagonal 2 
        m = pos & (pos >> (HEIGHT + 2));
        if((m & (m >> (2 * (HEIGHT + 2)))) != 0) return true;

        // vertical;
        m = pos & (pos >> 1);
        if((m & (m >> 2)) != 0) return true;

        return false;
    }

    /** GET MASKS */
    private static long top_mask(int col) {
        return (1L << (HEIGHT - 1)) << col * (HEIGHT + 1);
    }

    private static long bottom_mask(int col) {
        return 1L << col * (HEIGHT + 1);
    }
    
    private static long column_mask(int col) {
        return ((1L << HEIGHT) - 1) << col * (HEIGHT + 1); 
    }

    /** Turn the given position into a string.
     * @param pos The position to turn into a string.
     * @return The string representation of the given position.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if((current_position & bottom_mask(j)) != 0) {
                    sb.append("X");
                } else if((mask & bottom_mask(j)) != 0) {
                    sb.append("O");
                } else {
                    sb.append(".");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}

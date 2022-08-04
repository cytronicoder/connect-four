public class Position {
    public static final int WIDTH = 7;  // width of the board
    public static final int HEIGHT = 6; // height of the board
    public static final int MIN_SCORE = -(WIDTH * HEIGHT) / 2 + 3;
    public static final int MAX_SCORE = (WIDTH * HEIGHT + 1) / 2 - 3;

    private long current_position;
    private long mask;
    private int moves; // number of moves played since the beinning of the game.

    public static final long bottom(int width, int height) {
        return width == 0 ? 0 : bottom(width - 1, height) | 1L << (width - 1) * (height + 1);
    }

    private static final long bottom_mask = bottom(WIDTH, HEIGHT);
    private static final long board_mask = bottom_mask * ((1L << HEIGHT) - 1);
    
    public static final long column_mask(int col) {
        return ((1L << HEIGHT) - 1) << col * (HEIGHT + 1);
    }

    private static final long top_mask_col(int col) {
        return 1L << ((HEIGHT - 1) + col * (HEIGHT + 1));
    }

    private static final long bottom_mask_col(int col) {
        return 1L << (col * (HEIGHT + 1));
    }

    /** Constructor */
    public Position() {
        current_position = 0;
        mask = 0;
        moves = 0;
    }

    public Position(Position pos) {
        current_position = pos.current_position;
        mask = pos.mask;
        moves = pos.moves;
    }

    public void play(long move) {
        current_position ^= mask;
        mask |= move;
        moves++;
    }

    public int play(String seq) {
        for (int i = 0; i < seq.length(); i++) {
            int column = seq.charAt(i) - '1';
            if (column < 0 || column >= Position.WIDTH || !canPlay(column) || isWinningMove(column)) return i; // invalid move
            playColumn(column);
        }
        return seq.length();
    }

    public boolean canWinNext() {
        return (winning_position() & possible()) != 0;
    }

    public int getMoves() {
        return moves;
    }

    public long getKey() {
        return current_position + mask;
    }

    public long possibleNonLosingMoves() {
        long possible_mask = possible();
        long opponent_win = opponent_winning_position();
        long forced_moves = possible_mask & opponent_win;
        
        if (forced_moves != 0) {
            if ((forced_moves & (forced_moves - 1)) != 0) {
                return 0;
            } else {
                possible_mask = forced_moves;
            }
        }
        
        return possible_mask & ~(opponent_win >> 1);
    }

    public int moveScore(long move) {
        return popcount(compute_winning_position(current_position | move, mask));
    }

    private void playColumn(int col) {
        play((mask + bottom_mask_col(col)) & column_mask(col));
    }

    private boolean canPlay(int column) {
        return (mask & top_mask_col(column)) == 0;
    }

    private boolean isWinningMove(int column) {
        return (winning_position() & possible() & column_mask(column)) != 0;
    }

    private long winning_position() {
        return compute_winning_position(current_position, mask);
    }

    private long opponent_winning_position() {
        return compute_winning_position(current_position ^ mask, mask);
    }

    private long possible() {
        return (mask + bottom_mask) & board_mask;
    }

    private static int popcount(long m) {
        int c = 0; 

        for (c = 0; m != 0; c++) {
            m &= m - 1;
        }

        return c;
    }

    private long compute_winning_position(long position, long mask) {
        // vertical;
        long r = (position << 1) & (position << 2) & (position << 3);

        //horizontal
        long p = (position << (HEIGHT + 1)) & (position << 2 * (HEIGHT + 1));
        r |= p & (position << 3 * (HEIGHT + 1));
        r |= p & (position >> (HEIGHT + 1));
        p = (position >> (HEIGHT + 1)) & (position >> 2 * (HEIGHT + 1));
        r |= p & (position << (HEIGHT + 1));
        r |= p & (position >> 3 * (HEIGHT + 1));

        //diagonal 1
        p = (position << HEIGHT) & (position << 2 * HEIGHT);
        r |= p & (position << 3 * HEIGHT);
        r |= p & (position >> HEIGHT);
        p = (position >> HEIGHT) & (position >> 2 * HEIGHT);
        r |= p & (position << HEIGHT);
        r |= p & (position >> 3 * HEIGHT);

        //diagonal 2
        p = (position << (HEIGHT + 2)) & (position << 2 * (HEIGHT + 2));
        r |= p & (position << 3 * (HEIGHT + 2));
        r |= p & (position >> (HEIGHT + 2));
        p = (position >> (HEIGHT + 2)) & (position >> 2 * (HEIGHT + 2));
        r |= p & (position << (HEIGHT + 2));
        r |= p & (position >> 3 * (HEIGHT + 2));

        return r & (board_mask ^ mask);
    }
}
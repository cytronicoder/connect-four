public class Position {
    public static final int WIDTH = 7;
    public static final int HEIGHT = 6;
    public static final int MIN_SCORE = -(WIDTH * HEIGHT) / 2 + 3;
    public static final int MAX_SCORE = (WIDTH * HEIGHT + 1) / 2 - 3;

    private long current_position;
    private long mask;
    private int moves;

    public Position() {
        current_position = 0;
        mask = 0;
        moves = 0;
    }

    public Position(Position P) {
        current_position = P.current_position;
        mask = P.mask;
        moves = P.moves;
    }

    public boolean playable(int col) {
        return (mask & top_mask(col)) == 0;
    }

    public void play(int col) {
        current_position ^= mask;
        mask |= (mask + bottom_mask(col));
        moves++;
    }

    public int play(String seq) {
        for (int i = 0; i < seq.length(); i++) {
            int col = Character.getNumericValue(seq.charAt(i)) - 1;
            if (col < 0 || col >= WIDTH || !playable(col) || winsByPlaying(col)) {
                return i;
            }
            play(col);
        }
        return seq.length();
    }

    public boolean winsByPlaying(int col) {
        long pos = current_position;
        pos |= (mask + bottom_mask(col)) & column_mask(col);
        return alignment(pos);
    }

    public int getMoves() {
        return moves;
    }

    public long key() {
        return current_position + mask;
    }

    private boolean alignment(long pos) {
        long m = pos & (pos >> (HEIGHT + 1));
        if ((m & (m >> (2 * (HEIGHT + 1)))) != 0) {
            return true;
        }

        m = pos & (pos >> HEIGHT);
        if ((m & (m >> (2 * HEIGHT))) != 0) {
            return true;
        }

        m = pos & (pos >> (HEIGHT + 2));
        if ((m & (m >> (2 * (HEIGHT + 2)))) != 0) {
            return true;
        }

        m = pos & (pos >> 1);
        if ((m & (m >> 2)) != 0) {
            return true;
        }

        return false;
    }

    private long top_mask(int col) {
        return (1L << (HEIGHT - 1)) << col * (HEIGHT + 1);
    }

    private long bottom_mask(int col) {
        return 1L << col * (HEIGHT + 1);
    }

    private long column_mask(int col) {
        return ((1L << HEIGHT) - 1) << col * (HEIGHT + 1);
    }
}

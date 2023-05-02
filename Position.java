public class Position {
    public static final int WIDTH = 7;
    public static final int HEIGHT = 6;

    private int board[][] = new int[WIDTH][HEIGHT];
    private int tokens[] = new int[WIDTH];
    private int moves = 0;

    public Position() {
        for (int i = 0; i < WIDTH; i++) {
            tokens[i] = 0;
            for (int j = 0; j < HEIGHT; j++) {
                board[i][j] = 0;
            }
        }

        moves = 0;
    }

    public Position(Position P) {
        for (int i = 0; i < WIDTH; i++) {
            tokens[i] = P.tokens[i];
            for (int j = 0; j < HEIGHT; j++) {
                board[i][j] = P.board[i][j];
            }
        }

        moves = P.moves;
    }

    public boolean playable(int col) {
        return (tokens[col] < HEIGHT);
    }

    public void play(int col) {
        board[col][tokens[col]] = moves % 2 + 1;
        tokens[col]++;
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
        int player = (moves % 2) + 1;

        if (tokens[col] >= 3) {
            if (board[col][tokens[col] - 1] == player && board[col][tokens[col] - 2] == player
                    && board[col][tokens[col] - 3] == player) {
                return true;
            }
        }

        for (int dy = -1; dy <= 1; dy++) {
            int aligned = 0;
            for (int dx = -1; dx <= 1; dx += 2) {
                for (int x = col + dx, y = tokens[col] + dx * dy; x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT
                        && board[x][y] == player; aligned++) {
                    x += dx;
                    y += dx * dy;
                }
            }

            if (aligned >= 3) {
                return true;
            }
        }

        return false;
    }

    public int getMoves() {
        return moves;
    }
}

public class Solver {
    private long nodes = 0;
    private int[] columnOrder = new int[Position.WIDTH];

    public Solver() {
        nodes = 0;
        for (int i = 0; i < Position.WIDTH; i++) {
            columnOrder[i] = Position.WIDTH / 2 + (1 - 2 * (i % 2)) * (i + 1) / 2;
        }
    }

    private int negamax(Position pos, int alpha, int beta) {
        assert alpha < beta;
        nodes++;

        if (pos.getMoves() == Position.WIDTH * Position.HEIGHT) {
            return 0;
        }

        for (int x = 0; x < Position.WIDTH; x++) {
            if (pos.playable(x) && pos.winsByPlaying(x)) {
                return (Position.WIDTH * Position.HEIGHT + 1 - pos.getMoves()) / 2;
            }
        }

        int max = (Position.WIDTH * Position.HEIGHT - 1 - pos.getMoves()) / 2;
        if (beta > max) {
            beta = max;
            if (alpha >= beta) {
                return beta;
            }
        }

        for (int x = 0; x < Position.WIDTH; x++) {
            if (pos.playable(columnOrder[x])) {
                Position next = new Position(pos);
                next.play(columnOrder[x]);
                int score = -negamax(next, -beta, -alpha);
                if (score >= beta) {
                    return score;
                }
                if (score > alpha) {
                    alpha = score;
                }
            }
        }

        return alpha;
    }

    public int solve(Position pos) {
        nodes = 0;
        return negamax(pos, -Position.WIDTH * Position.HEIGHT / 2, Position.WIDTH * Position.HEIGHT / 2);
    }

    public long getNodes() {
        return nodes;
    }
}

class Timer {
    private long start;

    public Timer() {
        start = System.nanoTime();
    }

    public long elapsed() {
        return System.nanoTime() - start;
    }
}

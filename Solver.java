public class Solver {
    private long nodes = 0;

    private int negamax(Position pos) {
        nodes++;

        if (pos.getMoves() == Position.WIDTH * Position.HEIGHT) {
            return 0;
        }

        for (int x = 0; x < Position.WIDTH; x++) {
            if (pos.playable(x) && pos.winsByPlaying(x)) {
                return (Position.WIDTH * Position.HEIGHT + 1 - pos.getMoves()) / 2;
            }
        }

        int bestScore = -Position.WIDTH * Position.HEIGHT;
        for (int x = 0; x < Position.WIDTH; x++) {
            if (pos.playable(x)) {
                Position next = new Position(pos);
                next.play(x);
                int score = -negamax(next);
                if (score > bestScore) {
                    bestScore = score;
                }
            }
        }

        return bestScore;
    }

    public int solve(Position pos) {
        nodes = 0;
        return negamax(pos);
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

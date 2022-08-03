public class Solver {
    private long nodeCount;
    private int columnOrder[] = new int[Position.WIDTH];

    /** Constructor */
    public Solver() {
        nodeCount = 0;
        for (int i = 0; i < Position.WIDTH; i++) {
            columnOrder[i] = Position.WIDTH / 2 + (1 - 2 * (i % 2)) * (i + 1) / 2;
        }
    }

    /** A position has
     * a positive score if the current player has a winning move,
     * a negative score if the opponent has a winning move,
     * and a score of 0 if neither player has a winning move.
     */
    private int negamax(Position pos, int alpha, int beta) {
        nodeCount++;

        if (pos.getMoves() == Position.WIDTH * Position.HEIGHT) return 0;

        for (int x = 0; x < Position.WIDTH; x++) {
            if(pos.canMove(x) && pos.isWinningMove(x)) {
                return (Position.WIDTH * Position.HEIGHT + 1 - pos.getMoves())/2;
            }
        }
        
        int max = (Position.WIDTH * Position.HEIGHT - 1 - pos.getMoves())/2;
        
        if (beta > max) {
            beta = max;
            if (alpha >= beta) return beta;
        }
        
        for (int x = 0; x < Position.WIDTH; x++) {
            if (pos.canMove(columnOrder[x])) {
                Position newPos = new Position(pos);
                newPos.move(columnOrder[x]);
                int score = -negamax(newPos, -beta, -alpha);
                if (score >= beta) return score;
                if (score > alpha) alpha = score;
            }
        }
      return alpha;
    }

    public int solve(Position pos, boolean weak) {
        nodeCount = 0;
        if (weak) {
            return negamax(pos, -1, 1); 
        } else {
            return negamax(pos, -Position.WIDTH * Position.HEIGHT / 2, Position.WIDTH * Position.HEIGHT / 2); // a stronger heuristic that is more time consuming
        }
    }

    public long getNodeCount() {
        return nodeCount;
    }
}

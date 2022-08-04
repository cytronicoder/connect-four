public class Solver {
    private static long nodeCount;
    private static int columnOrder[] = new int[Position.WIDTH];

    private static TranspositionTable transpositionTable;

    /** Constructor */
    public Solver() {
        nodeCount = 0;
        transpositionTable = new TranspositionTable(8388593);

        reset();
        for (int i = 0; i < Position.WIDTH; i++) {
            columnOrder[i] = Position.WIDTH / 2 + (1 - 2 * (i % 2)) * (i + 1) / 2;
        }
    }

    /** A position has
     * a positive score if the current player has a winning move,
     * a negative score if the opponent has a winning move,
     * and a score of 0 if neither player has a winning move.
     */
    private static int negamax(Position pos, int alpha, int beta) {
        nodeCount++;

        long next = pos.possibleNonLoosingMoves();
        if (next == 0) return -(Position.WIDTH * Position.HEIGHT - pos.getMoves()) / 2;
        if (pos.getMoves() >= Position.WIDTH * Position.HEIGHT - 2) return 0;

        int min = -(Position.WIDTH * Position.HEIGHT - 2 - pos.getMoves()) / 2;
        if (alpha < min) {
            alpha = min;
            if(alpha >= beta) return alpha;
        }

        int max = (Position.WIDTH * Position.HEIGHT - 1 - pos.getMoves()) / 2;
        if (beta > max) {
            beta = max;
            if(alpha >= beta) return beta;
        }

        for (int x = 0; x < Position.WIDTH; x++) {
            if ((next & Position.column_mask(columnOrder[x])) != 0) {
                Position nextPos = new Position(pos);
                nextPos.play(columnOrder[x]);
                int score = -negamax(nextPos, -beta, -alpha);
                if(alpha >= beta) return alpha;
                if(score > alpha) alpha = score;
            }
        }

        transpositionTable.put(pos.getKey(), (byte) (alpha - Position.MIN_SCORE + 1));
        return alpha;
    }

    public int solve(Position pos, boolean weak) {
        if (pos.canWinNext()) {
            return (Position.WIDTH * Position.HEIGHT + 1 - pos.getMoves()) / 2;
        }
        
        int min = -(Position.WIDTH * Position.HEIGHT - pos.getMoves()) / 2;
        int max = (Position.WIDTH * Position.HEIGHT + 1 - pos.getMoves()) / 2;
        
        if(weak) {
            min = -1;
            max = 1;
        }

        // iteratively narrow the min-max exploration window
        while(min < max) {
            int med = min + (max - min) / 2;
            if (med <= 0 && min/2 < med) {
                med = min / 2;
            } else if (med >= 0 && med/2 > med) {
                med = med / 2;
            }
            
            // use a null depth window 
            int r = negamax(pos, med, med + 1);
            if(r <= med) {
                max = r;
            } else {
                min = r;
            }
        }

        return min;
    }

    public long getNodeCount() {
        return nodeCount;
    }

    public void reset() {
      nodeCount = 0;
      transpositionTable.reset();
    }
}

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

        if (pos.getMoves() == Position.WIDTH * Position.HEIGHT) return 0;

        for (int x = 0; x < Position.WIDTH; x++) {
            if(pos.canMove(x) && pos.isWinningMove(x)) {
                return (Position.WIDTH * Position.HEIGHT + 1 - pos.getMoves())/2;
            }
        }
        
        int max = (Position.WIDTH * Position.HEIGHT - 1 - pos.getMoves())/2;

        int val;
        if((val = transpositionTable.get(pos.getKey())) != 0) {
            max = val + Position.MIN_SCORE - 1;
        }

        
        if (beta > max) {
            beta = max;
            if (alpha >= beta) return beta;
        }
        
        for (int x = 0; x < Position.WIDTH; x++) {
            if (pos.canMove(columnOrder[x])) {
                Position newPos = new Position(pos);
                newPos.move(columnOrder[x]);
                int score = -negamax(newPos, -beta, -alpha);
                if (score >= beta) {
                    return score;
                }
                if (score > alpha) {
                    alpha = score;
                }
            }
        }

        transpositionTable.put(pos.getKey(), (byte) (alpha - Position.MIN_SCORE + 1));
        return alpha;
    }

    /** Find the best column to move in using the negamax algorithm.
     * @param pos The current position.
     * @return The best column to move in.
     */
    public int findBestMove(Position pos) {
        int bestChoice = -1;
        int bestScore = -Position.WIDTH * Position.HEIGHT;
        for (int x = 0; x < Position.WIDTH; x++) {
            if (pos.canMove(x)) {
                Position newPos = new Position(pos);
                newPos.move(x);
                int score = -negamax(newPos, -Position.WIDTH * Position.HEIGHT, Position.WIDTH * Position.HEIGHT);
                if (score > bestScore) {
                    bestChoice = x;
                    bestScore = score;
                }
            }
        }
        return bestChoice;
    }

    public int solve(Position pos, boolean weak) {
        int min = -(Position.WIDTH * Position.HEIGHT - pos.getMoves()) / 2;
        int max = (Position.WIDTH * Position.HEIGHT + 1 - pos.getMoves()) / 2;
        
        // weak search configuration
        if (weak) {
            min = -1;
            max = 1;
        }

        while(min < max) {
            int med = min + (max - min) / 2;

            if ((med <= 0) && (min / 2 < med)) {
                med = min/2;
            } else if ((med >= 0) && (max/2 > med)) {
                med = max/2;
            }
            
            // use a null depth window
            int r = negamax(pos, med, med + 1);
            if (r <= med) {
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

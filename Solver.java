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

        long next = pos.possibleNonLosingMoves();
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

        MoveSorter moves = new MoveSorter();

        for(int i = Position.WIDTH - 1; i >= 0; i--) {
            long move;
            if((move = next & Position.column_mask(columnOrder[i])) != 0) {
                moves.add(move, pos.moveScore(move));
            }
        }

        long nextMove;
        while ((nextMove = moves.getNext()) != 0) {
            Position next_pos = new Position(pos);
            next_pos.play(nextMove);
            int score = -negamax(next_pos, -beta, -alpha);
            if (score >= beta) return score;
            if(score > alpha) alpha = score;
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

    /** Get the best move for the current player to play.
     * @param pos the current position
     * @return the column number of the best move
     */
    public int chooseMove(Position pos) {
        // TODO: Implement a method that returns the best column for the current player to move to.
        return 0;
    }
    
    /** Reset the solver */
    public void reset() {
        nodeCount = 0;
        transpositionTable.reset();
    }
    
    /** Get the number of nodes visited */
    public long getNodeCount() {
        return nodeCount;
    }
}

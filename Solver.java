import java.util.Scanner;
import java.io.*;

// https://stackoverflow.com/a/45444716
import util.ConsoleColors;

public class Solver {
    private long nodeCount;
    private int columnOrder[] = new int[Position.WIDTH];

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
            return negamax(pos, -Position.WIDTH * Position.HEIGHT / 2, Position.WIDTH * Position.HEIGHT / 2);
        }
    }

    public long getNodeCount() {
        return nodeCount;
    }

    public Solver() {
        nodeCount = 0;
        for (int i = 0; i < Position.WIDTH; i++) {
            columnOrder[i] = Position.WIDTH / 2 + (1 - 2 * (i % 2)) * (i + 1) / 2;
        }
    }

    // Driver code
    public static void main(String[] args) throws Exception {
        Solver solver = new Solver();
        Scanner in = new Scanner(System.in);

        // Configuration
        boolean weak = false;

        /** Read in the test data from the test folder
         * Parse the input and call the solver to get the score
         * If the scores match, then the test passed
         */
        File test = new File("test/End-Easy.txt");
        BufferedReader br = new BufferedReader(new FileReader(test));
        
        String line;

        // line = br.readLine();
        for (int i = 0; (line = br.readLine()) != null; i++) {
            Position pos = new Position();

            String[] tokens = line.split(" ");
            String moves = tokens[0];
            int score = Integer.parseInt(tokens[1]);

            int startTime = (int) System.currentTimeMillis();
            if (pos.move(moves) != moves.length()) {
                System.out.print(ConsoleColors.RED + "Move " + (pos.getMoves() + 1) + " failed on line " + i + ConsoleColors.RESET);
            } else if (solver.solve(pos, weak) != score) {
                System.out.print(ConsoleColors.RED_BRIGHT + "Score failed on line " + i + ": Expected " + score + ", got " + solver.solve(pos, weak) + ": " + moves + ConsoleColors.RESET);
            } else {
                System.out.print(ConsoleColors.GREEN + "Test passed on line " + i + ": " + moves + " " + score + ConsoleColors.RESET);
            }

            // Print the time taken to solve the test
            int endTime = (int) System.currentTimeMillis();
            System.out.println(" in " + (endTime - startTime) + "ms");
        }

        br.close();
        
        /** User input for the number of moves to make.
         * The board is then initialized with a random sequence of moves.
         */
        // System.out.print("Enter the number of moves: ");
        // int moves = in.nextInt();
        // for (int j = 0; j < moves; j++) {
        //     Position pos = new Position();
        //     int column = (int) (Math.random() * Position.WIDTH);
        //     pos.move(column);
        //     System.out.println("Player " + (pos.getMoves() % 2 + 1) + " moved to column " + (column + 1));
        //     System.out.println(pos);
        //     if (pos.isWon()) {
        //         System.out.println("Player " + (pos.getMoves() % 2 + 1) + " wins!");
        //         break;
        //     }
        // }
        
        /** You can also enter a move string to make a move.
         * The move string is a sequence of characters '1' to '7' representing the column to move to.
         */
        // System.out.print("Enter a move string: ");
        // String move = in.next();
        // pos.move(move);

        // System.out.println("Score: " + solver.solve(pos));
        // System.out.println("Nodes: " + solver.getNodeCount());

        in.close();
    }
}

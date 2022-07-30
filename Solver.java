import java.util.Scanner;
import java.io.*;

public class Solver {
    private long nodeCount;

    /** A position has
     * a positive score if the current player has a winning move,
     * a negative score if the opponent has a winning move,
     * and a score of 0 if neither player has a winning move.
     */
    private int negamax(Position pos) {
        nodeCount++;

        if (pos.getMoves() == Position.WIDTH * Position.HEIGHT) {
            return 0;
        }

        for (int x = 0; x < Position.WIDTH; x++) {
            if(pos.canMove(x) && pos.canWin(x)) {
                return (Position.WIDTH * Position.HEIGHT + 1 - pos.getMoves())/ 2;
            }
        }

        int bestScore = -Position.WIDTH * Position.HEIGHT;

        for (int x = 0; x < Position.WIDTH; x++) {
            if (pos.canMove(x)) {
                Position newPos = new Position(pos);
                newPos.move(x);
                int score = -negamax(newPos);
                if (score > bestScore) {
                    bestScore = score;
                }
            }
        }

        return bestScore;
    }

    public int solve(Position pos) {
        nodeCount = 0;
        return negamax(pos);
    }

    public long getNodeCount() {
        return nodeCount;
    }

    // Driver code
    public static void main(String[] args) throws Exception {
        Solver solver = new Solver();
        Position pos = new Position();
        Scanner in = new Scanner(System.in);

        /** Read in the test data from the test folder
         * Parse the input and call the solver to get the score
         * If the scores match, then the test passed
         */
        // File test = new File("test/End-Easy.txt");
        // BufferedReader br = new BufferedReader(new FileReader(test));

        // String line;
        // int lineNum = 0;

        // line = br.readLine();
        // while ((line = br.readLine()) != null) {
        //     String[] tokens = line.split(" ");
        //     String moves = tokens[0];
        //     int score = Integer.parseInt(tokens[1]);
        //     pos.move(moves);

        //     int solverScore = solver.solve(pos);
        //     if (solverScore != score) {
        //         System.out.println("Test " + lineNum + " failed: " + solverScore + " != " + score);
        //     } else {
        //         System.out.println("Test " + lineNum + " passed");
        //     }
        //     lineNum++;
        // }

        // br.close();
        
        /** User input for the number of moves to make.
         * The board is then initialized with a random sequence of moves.
         */
        System.out.print("Enter the number of moves: ");
        int moves = in.nextInt();
        for (int j = 0; j < moves; j++) {
            int column = (int) (Math.random() * Position.WIDTH);
            pos.move(column);
            System.out.println("Player " + (pos.getMoves() % 2 + 1) + " moved to column " + (column + 1));
            System.out.println(pos);
            if (pos.isWon()) {
                System.out.println("Player " + (pos.getMoves() % 2 + 1) + " wins!");
                break;
            }
        }
        
        /** You can also enter a move string to make a move.
         * The move string is a sequence of characters '1' to '7' representing the column to move to.
         */
        // System.out.print("Enter a move string: ");
        // String move = in.next();
        // pos.move(move);

        System.out.println("Score: " + solver.solve(pos));
        System.out.println("Nodes: " + solver.getNodeCount());

        in.close();
    }
}

import java.util.Scanner;
import java.io.*;

// https://stackoverflow.com/a/45444716
import util.ConsoleColors;

public class SolverTest {
    // Driver code
    public static void main(String[] args) throws Exception {
        Solver solver = new Solver();
        Scanner in = new Scanner(System.in);

        /** Configuration
         * Heuristic is set to false by default.
         */
        boolean weak = false;

        /** Read in the test data from the test folder
         * Parse the input and call the solver to get the score
         * If the scores match, then the test passed
         */
        File test = new File("test/Middle-Easy.txt");
        BufferedReader br = new BufferedReader(new FileReader(test));
        
        String line;

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

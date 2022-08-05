import java.util.Scanner;
import java.io.*;

// custom import
import util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        // create objects
        Position pos = new Position();
        Solver solver = new Solver();

        Scanner in = new Scanner(System.in);

        // read in the text from instructions.txt and print the instructions
        File file = new File("instructions.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));

        String line;
        try {
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        br.close();
        
        // newline
        System.out.println();

        // get user input for the move string and the heuristic setting
        System.out.print("Enter a move string: ");
        String moves = in.nextLine();

        System.out.print("Enter a heuristic setting (0 for weak, 1 for strong): ");
        boolean weak = (in.nextInt() == 0);

        // newline
        System.out.println();

        System.out.println("Solving...");

        // newline
        System.out.println();

        // play the move string
        int moveCount = pos.play(moves);
        if (moveCount != moves.length()) {
            System.out.println("Invalid move: shutting down");
        } else {
            // solve the position
            int startTime = (int) System.currentTimeMillis();
            int score = solver.solve(pos, weak);
            int endTime = (int) System.currentTimeMillis();

            System.out.println(ConsoleColors.GREEN + "Position solved in " + pos.getMoves() + " moves with score " + score + " in " + (endTime - startTime) + "ms" + ConsoleColors.RESET);
            System.out.println("Best move: " + solver.chooseMove(pos));
        }
        
        in.close();
    }
}

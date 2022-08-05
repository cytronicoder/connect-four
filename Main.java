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

        while (true) {
            // newline
            System.out.println();

            // get user input for the move string and the heuristic setting
            System.out.print("Enter a move string: ");
            String move = in.next();
            System.out.print("Weak heuristic? (true/false): ");
            boolean weak = in.nextBoolean();

            // play the move string
            int moveCount = pos.play(move);
            if (moveCount != move.length()) {
                System.out.println("Invalid move: shutting down");
                break;
            } else {
                // solve the position
                int score = solver.solve(pos, weak);
                System.out.println(ConsoleColors.GREEN + "Score: " + score + ConsoleColors.RESET);
                // TODO: Implement a method that returns the best column for the current player to move to
            }
        }
        
        in.close();
    }
}

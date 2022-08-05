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

        // clear console
        ClearConsole.clear();

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

        // generate a random move string of length between 10 and 20
        // keep generating a random move string until the string doesn't have any winning moves
        int length = (int) (Math.random() * 10 + 10);
        String moveString = "";

        while (true) {
            Position sudoPosition = new Position();

            for (int i = 0; i < length; i++) {
                moveString += (int) (Math.random() * 7 + 1);
            }
            
            int moveCount = sudoPosition.play(moveString);
            if (moveCount == moveString.length()) {
                break;
            } else {
                moveString = "";
            }
        }

        // get user input for the move string and the heuristic setting
        System.out.print("Do you want to use a random move string? (y/n): ");
        String input = in.nextLine();

        if (input.equals("y")) {
            System.out.println("Using random move string: " + moveString);
        } else {
            System.out.print("Enter the move string: ");
            moveString = in.nextLine();
        }

        System.out.print("Enter a heuristic setting (0 for weak, 1 for strong): ");
        boolean weak = (in.nextInt() == 0);

        // newline
        System.out.println();

        System.out.println("Solving...");

        // newline
        System.out.println();

        // play the move string
        int moveCount = pos.play(moveString);
        if (moveCount != moveString.length()) {
            System.out.println("Invalid move: shutting down");
        } else {
            // solve the position
            int startTime = (int) System.currentTimeMillis();
            int score = solver.solve(pos, weak);
            int endTime = (int) System.currentTimeMillis();

            System.out.println("Position solved in " + pos.getMoves() + " moves in " + (endTime - startTime) + "ms: Score = " + score);
            System.out.println(ConsoleColors.GREEN + "Optimal next move: column " + solver.chooseMove(pos, weak) + ConsoleColors.RESET);
        }
        
        in.close();
    }
}

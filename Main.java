import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Solver solver = new Solver();

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter test batch to run: ");
        String testBatch = scanner.nextLine();
        scanner.close();
        
        try {
            BufferedReader br = new BufferedReader(new FileReader("test/" + testBatch));
            String line;

            System.out.println("Running test batch " + testBatch + "...");

            while ((line = br.readLine()) != null) {
                String[] split = line.split(" ");
                String seq = split[0];
                int expectedScore = Integer.parseInt(split[1]);

                Position P = new Position();
                P.play(seq);
                
                int score = solver.solve(P);

                if (score != expectedScore) {
                    System.out.println("Error: " + seq + " " + score + " " + expectedScore);
                    br.close();
                    return;
                }

                System.out.println(seq + " " + score);
            }

            br.close();

            System.out.println("Test batch " + testBatch + " passed!");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

package util;

public class ClearConsole {
    public static void clear() {
        System.out.print("\033[H\033[2J"); // Esc + move cursor + clear screen
        System.out.flush();
    }
}

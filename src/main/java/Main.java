import java.util.Scanner;

/**
 * Main entry point for the Dungeon Crawler game.
 * Instantiates the game and starts the adventure.
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        try {
            Game game = new Game(scanner);
            game.start();
        } finally {
            scanner.close();
        }
    }
}


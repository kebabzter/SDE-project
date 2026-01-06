import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== DUNGEON CRAWLER ===");
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        
        System.out.println("Welcome, " + name + "!");
        System.out.println("Your adventure begins...");
        
        scanner.close();
    }
}


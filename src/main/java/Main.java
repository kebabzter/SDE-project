import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        printLogo();
        
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        
        System.out.println("\nWelcome, " + name + "!");
        System.out.println("Your descent begins...");
        
        scanner.close();
    }
    
    private static void printLogo() {
        System.out.println();
        System.out.println("  ╔═════════════════════════════════════════════════════════════════╗");
        System.out.println("  ║                                                                 ║");
        System.out.println("  ║         ████████╗██╗  ██╗███████╗   ██████╗ ██╗████████╗        ║");
        System.out.println("  ║         ╚══██╔══╝██║  ██║██╔════╝   ██╔══██╗██║╚══██╔══╝        ║");
        System.out.println("  ║            ██║   ███████║█████╗     ██████╔╝██║   ██║           ║");
        System.out.println("  ║            ██║   ██╔══██║██╔══╝     ██╔═══╝ ██║   ██║           ║");
        System.out.println("  ║            ██║   ██║  ██║███████╗   ██║     ██║   ██║           ║");
        System.out.println("  ║            ╚═╝   ╚═╝  ╚═╝╚══════╝   ╚═╝     ╚═╝   ╚═╝           ║");
        System.out.println("  ║                                                                 ║");
        System.out.println("  ║                        ─────────────────                        ║");
        System.out.println("  ║                         A Dungeon Awaits                        ║");
        System.out.println("  ║                        ─────────────────                        ║");
        System.out.println("  ║                                                                 ║");
        System.out.println("  ╚═════════════════════════════════════════════════════════════════╝");
        System.out.println();
    }
}


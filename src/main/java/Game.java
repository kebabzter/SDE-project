import java.util.Scanner;

/**
 * Game class responsible for game flow and mechanics.
 * Follows the Single Responsibility Principle.
 */
public class Game {
    private final Scanner scanner;
    private Hero hero;

    public Game(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Starts the game and handles the main flow.
     */
    public void start() {
        printLogo();
        String playerName = getPlayerName();
        HeroType selectedType = selectHero();
        hero = new Hero(playerName, selectedType);
        
        System.out.println("\n" + playerName + ", you have chosen the path of the " + hero.getType().getName() + "!");
        hero.displayStats();
        
        System.out.println("Your adventure begins...");
    }

    /**
     * Gets the player's name from input.
     */
    private String getPlayerName() {
        System.out.print("Enter your name: ");
        return scanner.nextLine().trim();
    }

    /**
     * Displays hero selection menu and gets player choice.
     */
    private HeroType selectHero() {
        System.out.println("\n╔═════════════════════════════════════════╗");
        System.out.println("║         SELECT YOUR HERO CLASS          ║");
        System.out.println("╚═════════════════════════════════════════╝\n");

        for (HeroType type : HeroType.values()) {
            System.out.println(type.getId() + ". " + type.getName());
            System.out.println("   " + type.getDescription());
            System.out.println("   Stats: STR:" + type.getStrength() + 
                             " INT:" + type.getIntelligence() + 
                             " AGI:" + type.getAgility() + "\n");
        }

        return getValidHeroSelection();
    }

    /**
     * Validates and returns hero selection from player input.
     */
    private HeroType getValidHeroSelection() {
        int choice;
        while (true) {
            System.out.print("Select your hero (1-3): ");
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
                HeroType selected = HeroType.fromId(choice);
                if (selected != null) {
                    return selected;
                }
                System.out.println("Invalid choice. Please enter 1, 2, or 3.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    /**
     * Prints the game logo/banner.
     */
    private void printLogo() {
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

    public Hero getHero() {
        return hero;
    }
}

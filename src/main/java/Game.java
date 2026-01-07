import java.util.Scanner;

public class Game {
    private static Game instance;
    private final Scanner scanner;
    private Hero hero;

    // Private constructor prevents external instantiation
    private Game() {
        this.scanner = new Scanner(System.in);
    }
    
    public static Game getInstance() {
        if (instance == null) {
            synchronized (Game.class) {
                if (instance == null) {
                    instance = new Game();
                }
            }
        }
        return instance;
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
        
        System.out.println("\nPress ENTER to begin your descent into The Pit...");
        scanner.nextLine();
        
        gameLoop();
    }
    
    /**
     * Main game loop - generates rooms and handles combat/shop cycle.
     */
    private void gameLoop() {
        int currentLevel = 1;
        Shop shop = new Shop(scanner);
        
        while (hero.isAlive()) {
            // Generate room with enemies
            Room room = generateRoom(currentLevel);
            room.displayInfo();
            
            // Combat phase
            while (!room.isCleared() && hero.isAlive()) {
                Enemy enemy = room.getNextEnemy();
                if (enemy != null) {
                    combat(enemy);
                }
            }
            
            // Check if hero survived
            if (!hero.isAlive()) {
                break;
            }
            
            // Room cleared!
            System.out.println("\n✓ Room cleared!");
            
            // Shop phase
            System.out.println("\nWould you like to visit the shop? [Y/N]");
            String choice = scanner.nextLine().trim().toUpperCase();
            if (choice.equals("Y") || choice.equals("YES")) {
                shop.open(hero);
            }
            
            // Next level
            currentLevel++;
            System.out.println("\nPress ENTER to descend deeper...");
            scanner.nextLine();
        }
        
        gameOver(currentLevel);
    }
    
    /**
     * Generates a room with enemies scaled to the level.
     * Uses EnemyFactory to create different enemy types.
     */
    private Room generateRoom(int level) {
        Room room = new Room(level);
        
        // Number of enemies increases with level
        int enemyCount = 1 + (level / 3);
        
        for (int i = 0; i < enemyCount; i++) {
            // Use EnemyFactory to create enemies by index, cycling through types
            Enemy enemy = EnemyFactory.createEnemyByIndex(i, level);
            room.addEnemy(enemy);
        }
        
        return room;
    }
    
    /**
     * Handles combat between hero and enemy.
     * Includes state management for status effects.
     */
    private void combat(Enemy enemy) {
        System.out.println("\n⚔ A " + enemy.getName() + " appears!");
        
        while (enemy.isAlive() && hero.isAlive()) {
            System.out.println("\n─────────────────────────────────────");
            System.out.println("You: " + hero.getHealth() + "/" + hero.getMaxHealth() + " HP | " + 
                             enemy.getName() + ": " + enemy.getHealth() + "/" + enemy.getMaxHealth() + " HP");
            System.out.println("Status: " + hero.getCurrentState().getStateName());
            System.out.println("─────────────────────────────────────");
            
            // Check if hero is stunned
            if (!hero.canAct()) {
                System.out.println("⭐ You are stunned and cannot act!");
            } else {
                System.out.println("[1] Attack");
                System.out.println("[2] Defend");
            }
            System.out.print("> ");
            
            String action = scanner.nextLine().trim();
            
            if (hero.canAct() && action.equals("1")) {
                // Hero attacks
                int damage = hero.getAttack();
                enemy.takeDamage(damage);
                System.out.println("You attack for " + damage + " damage!");
                
                if (!enemy.isAlive()) {
                    System.out.println("✓ " + enemy.getName() + " defeated!");
                    hero.addGold(enemy.getGoldReward());
                    System.out.println("+ " + enemy.getGoldReward() + " gold");
                    break;
                }
                
                // Chance for enemy to apply status effect
                enemy.applyRandomEffect(hero);
                
                // Enemy attacks back
                int enemyDamage = enemy.getAttack();
                hero.takeDamage(enemyDamage);
                System.out.println(enemy.getName() + " attacks for " + enemyDamage + " damage!");
                
            } else if (hero.canAct() && action.equals("2")) {
                // Hero defends
                System.out.println("You brace for impact!");
                int enemyDamage = Math.max(1, enemy.getAttack() / 2);
                hero.takeDamage(enemyDamage);
                System.out.println(enemy.getName() + " attacks for " + enemyDamage + " damage (reduced)!");
            } else if (!hero.canAct()) {
                // Hero is stunned or otherwise unable to act
                int enemyDamage = enemy.getAttack();
                hero.takeDamage(enemyDamage);
                System.out.println(enemy.getName() + " attacks you for " + enemyDamage + " damage!");
            } else {
                System.out.println("Invalid action!");
                continue;
            }
            
            // Apply poison damage if poisoned
            if (hero.getCurrentState() instanceof PoisonedState) {
                PoisonedState poisoned = (PoisonedState) hero.getCurrentState();
                int poisonDamage = poisoned.getPoisonDamage();
                hero.takeDamage(poisonDamage);
                System.out.println("☠ Poison damage: " + poisonDamage + " HP!");
            }
            
            // Update hero state (decrement duration, transition if needed)
            hero.updateState();
        }
    }
    
    /**
     * Displays game over screen.
     */
    private void gameOver(int finalLevel) {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║            GAME OVER                  ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.println("\nYou reached level: " + finalLevel);
        System.out.println("Final gold: " + hero.getGold());
        System.out.println("\nThank you for playing The Pit!");
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

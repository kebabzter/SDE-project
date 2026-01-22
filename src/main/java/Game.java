import java.util.Scanner;

import factory.EnemyFactory;
import model.Enemy;
import model.Hero;
import model.HeroType;
import model.Room;
import strategy.EnemyAI;

public class Game {
    private static Game instance;
    private final Scanner scanner;
    private Hero hero;

    private Game() {
        this.scanner = new Scanner(System.in);
    }
    
    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
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

    public void start() {
        clearScreen();
        printLogo();
        String playerName = getPlayerName();
        
        clearScreen();
        printLogo();
        HeroType selectedType = selectHero();
        hero = new Hero(playerName, selectedType);
        
        clearScreen();
        System.out.println("\n" + playerName + ", you have chosen the path of the " + hero.getType().getName() + "!");
        hero.displayStats();
        
        System.out.println("\nPress ENTER to begin your descent into The Pit...");
        scanner.nextLine();
        
        gameLoop();
    }
    
    private void gameLoop() {
        int currentLevel = 1;
        Shop shop = new Shop(scanner);
        
        while (hero.isAlive()) {
            clearScreen();
            Room room = generateRoom(currentLevel);
            room.displayInfo();
            
            while (!room.isCleared() && hero.isAlive()) {
                Enemy enemy = room.getNextEnemy();
                if (enemy != null) {
                    combat(enemy);
                }
            }
            
            if (!hero.isAlive()) {
                break;
            }
            
            clearScreen();
            System.out.println("\n✓ Room cleared!");
            
            System.out.println("\nWould you like to visit the shop? [Y/N]");
            String choice = scanner.nextLine().trim().toUpperCase();
            if (choice.equals("Y") || choice.equals("YES")) {
                clearScreen();
                shop.open(hero);
            }
            
            currentLevel++;
            System.out.println("\nPress ENTER to descend deeper...");
            scanner.nextLine();
        }
        
        clearScreen();
        gameOver(currentLevel);
    }
    
    private Room generateRoom(int level) {
        Room room = new Room(level);
        int enemyCount = 1 + (level / 3);
        
        for (int i = 0; i < enemyCount; i++) {
            Enemy enemy = EnemyFactory.createEnemyByIndex(i, level);
            room.addEnemy(enemy);
        }
        
        return room;
    }
    
    private void combat(Enemy enemy) {
        clearScreen();
        System.out.println("\n⚔ A " + enemy.getName() + " appears!");
        System.out.println("\nPress ENTER to start combat...");
        scanner.nextLine();
        
        while (enemy.isAlive() && hero.isAlive()) {
            clearScreen();
            System.out.println("\n╔═══════════════════════════════════════╗");
            System.out.println("║            ⚔ COMBAT ⚔                 ║");
            System.out.println("╠═══════════════════════════════════════╣");
            System.out.printf("║ You:   %3d/%3d HP                     ║%n", 
                hero.getHealth(), hero.getMaxHealth());
            
            String enemyInfo = String.format("(%s)", enemy.getName());
            System.out.printf("║ Enemy: %3d/%3d HP %-19s║%n", 
                enemy.getHealth(), enemy.getMaxHealth(), enemyInfo);
            
            System.out.printf("║ Status: %-29s ║%n", hero.getCurrentState().getStateName());
            System.out.println("╠═══════════════════════════════════════╣");
            
            if (!hero.canAct()) {
                System.out.println("║ ⭐ You are stunned and cannot act!   ║");
                System.out.println("╚═══════════════════════════════════════╝");
            } else {
                System.out.println("║ [1] Attack                            ║");
                System.out.println("║ [2] Defend                            ║");
                System.out.println("╚═══════════════════════════════════════╝");
            }
            System.out.print("> ");
            
            String action = scanner.nextLine().trim();
            
            if (hero.canAct() && action.equals("1")) {
                int damage = hero.getAttack();
                enemy.takeDamage(damage);
                System.out.println("\n⚔ You attack for " + damage + " damage!");
                
                if (!enemy.isAlive()) {
                    System.out.println("✓ " + enemy.getName() + " defeated!");
                    hero.addGold(enemy.getGoldReward());
                    System.out.println("+ " + enemy.getGoldReward() + " gold");
                    System.out.println("\nPress ENTER to continue...");
                    scanner.nextLine();
                    break;
                }
                
                enemy.applyRandomEffect(hero);
                
                EnemyAI.Action enemyAction = enemy.decideAction(hero.getHealth(), hero.getMaxHealth());
                performEnemyAction(enemy, hero, enemyAction);
                
                System.out.println("\nPress ENTER to continue...");
                scanner.nextLine();
                
            } else if (hero.canAct() && action.equals("2")) {
                System.out.println("\n🛡 You brace for impact!");
                
                EnemyAI.Action enemyAction = enemy.decideAction(hero.getHealth(), hero.getMaxHealth());
                
                if (enemyAction == EnemyAI.Action.ATTACK) {
                    int enemyDamage = Math.max(1, enemy.getAttack() / 2);
                    hero.takeDamage(enemyDamage);
                    System.out.println("☠ " + enemy.getName() + " attacks for " + enemyDamage + " damage (reduced)!");
                } else {
                    performEnemyAction(enemy, hero, enemyAction);
                }
                
                System.out.println("\nPress ENTER to continue...");
                scanner.nextLine();
            } else if (!hero.canAct()) {
                EnemyAI.Action enemyAction = enemy.decideAction(hero.getHealth(), hero.getMaxHealth());
                performEnemyAction(enemy, hero, enemyAction);
                
                System.out.println("\nPress ENTER to continue...");
                scanner.nextLine();
            } else {
                System.out.println("Invalid action!");
                continue;
            }
            
            // Apply state damage (e.g. poison)
            int turnDamage = hero.getCurrentState().getTurnDamage();
            if (turnDamage > 0) {
                hero.takeDamage(turnDamage);
                System.out.println("☠ " + hero.getCurrentState().getStateName() + " damage: " + turnDamage + " HP!");
            }
            
            hero.updateState();
        }
    }
    
    private void performEnemyAction(Enemy enemy, Hero hero, EnemyAI.Action action) {
        switch (action) {
            case ATTACK:
                int enemyDamage = enemy.getAttack();
                hero.takeDamage(enemyDamage);
                System.out.println(enemy.getName() + " (" + enemy.getStrategy().getStrategyName() + ") attacks for " + enemyDamage + " damage!");
                break;
            case DEFEND:
                System.out.println(enemy.getName() + " (" + enemy.getStrategy().getStrategyName() + ") braces for impact!");
                break;
            case HEAL:
                int healAmount = Math.max(5, enemy.getMaxHealth() / 10);
                enemy.heal(healAmount);
                System.out.println(enemy.getName() + " (" + enemy.getStrategy().getStrategyName() + ") heals for " + healAmount + " HP!");
                break;
        }
    }
    
    private void gameOver(int finalLevel) {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║            GAME OVER                  ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.println("\nYou reached level: " + finalLevel);
        System.out.println("Final gold: " + hero.getGold());
        System.out.println("\nThank you for playing The Pit!");
    }

    private String getPlayerName() {
        System.out.print("Enter your name: ");
        return scanner.nextLine().trim();
    }

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

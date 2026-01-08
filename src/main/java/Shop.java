import java.util.Scanner;

public class Shop {
    private final Scanner scanner;
    
    public Shop(Scanner scanner) {
        this.scanner = scanner;
    }
    
    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void open(Hero hero) {
        boolean shopping = true;
        
        while (shopping) {
            clearScreen();
            displayShop(hero);
            
            System.out.print("\nYour choice: ");
            String input = scanner.nextLine().trim();
            
            switch (input) {
                case "1" -> buyHealthPotion(hero);
                case "2" -> buyAttackUpgrade(hero);
                case "3" -> buyDefenseUpgrade(hero);
                case "4" -> buyFullHeal(hero);
                case "5" -> enchantWeapon(hero);
                case "6" -> viewInventory(hero);
                case "0" -> shopping = false;
                default -> {
                    System.out.println("Invalid choice. Please try again.");
                    System.out.println("\nPress ENTER to continue...");
                    scanner.nextLine();
                }
            }
        }
        
        clearScreen();
        System.out.println("\nLeaving shop...\n");
    }
    
    private void displayShop(Hero hero) {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║            THE PIT SHOP               ║");
        System.out.println("╠═══════════════════════════════════════╣");
        System.out.println("║  Your Gold: " + hero.getGold() + "g                   ");
        System.out.println("╠═══════════════════════════════════════╣");
        System.out.println("║  [1] Health Potion    - 20g           ║");
        System.out.println("║      Restore 30 HP                    ║");
        System.out.println("║                                       ║");
        System.out.println("║  [2] Attack Upgrade   - 50g           ║");
        System.out.println("║      +5 Attack (Permanent)            ║");
        System.out.println("║                                       ║");
        System.out.println("║  [3] Defense Upgrade  - 50g           ║");
        System.out.println("║      +3 Defense (Permanent)           ║");
        System.out.println("║                                       ║");
        System.out.println("║  [4] Full Heal        - 40g           ║");
        System.out.println("║      Restore to max HP                ║");
        System.out.println("║                                       ║");
        System.out.println("║  [5] Enchant Weapon   - 80g           ║");
        System.out.println("║      Add +7 damage                    ║");
        System.out.println("║                                       ║");
        System.out.println("║  [6] View Inventory                   ║");
        System.out.println("║                                       ║");
        System.out.println("║  [0] Leave Shop                       ║");
        System.out.println("╚═══════════════════════════════════════╝");
    }
    
    private void buyHealthPotion(Hero hero) {
        int cost = 20;
        if (hero.getGold() >= cost) {
            hero.removeGold(cost);
            hero.heal(30);
            System.out.println("✓ Purchased Health Potion! Restored 30 HP.");
        } else {
            System.out.println("✗ Not enough gold!");
        }
        System.out.println("\nPress ENTER to continue...");
        scanner.nextLine();
    }
    
    private void buyAttackUpgrade(Hero hero) {
        int cost = 50;
        if (hero.getGold() >= cost) {
            hero.removeGold(cost);
            hero.increaseAttack(5);
            System.out.println("✓ Attack increased by 5!");
        } else {
            System.out.println("✗ Not enough gold!");
        }
        System.out.println("\nPress ENTER to continue...");
        scanner.nextLine();
    }
    
    private void buyDefenseUpgrade(Hero hero) {
        int cost = 50;
        if (hero.getGold() >= cost) {
            hero.removeGold(cost);
            hero.increaseDefense(3);
            System.out.println("✓ Defense increased by 3!");
        } else {
            System.out.println("✗ Not enough gold!");
        }
        System.out.println("\nPress ENTER to continue...");
        scanner.nextLine();
    }
    
    private void buyFullHeal(Hero hero) {
        int cost = 40;
        if (hero.getGold() >= cost) {
            hero.removeGold(cost);
            hero.fullHeal();
            System.out.println("✓ Fully healed!");
        } else {
            System.out.println("✗ Not enough gold!");
        }
        System.out.println("\nPress ENTER to continue...");
        scanner.nextLine();
    }
    
    private void enchantWeapon(Hero hero) {
        int cost = 80;
        if (hero.getGold() >= cost) {
            hero.removeGold(cost);
            
            // Wrap current weapon with enchantment (DECORATOR)
            Weapon currentWeapon = hero.getWeapon();
            EnchantedWeapon enchantedWeapon = new EnchantedWeapon(currentWeapon, "Flame", 7);
            hero.equipWeapon(enchantedWeapon);
            
            System.out.println("✓ Weapon enchanted with Flame! +7 damage bonus!");
            System.out.println("  New weapon: " + enchantedWeapon.getDescription());
        } else {
            System.out.println("✗ Not enough gold!");
        }
        System.out.println("\nPress ENTER to continue...");
        scanner.nextLine();
    }
    
    private void viewInventory(Hero hero) {
        clearScreen();
        System.out.println("\n═══════════════════════════════════════");
        System.out.println("          INVENTORY            ");
        System.out.println("═══════════════════════════════════════");
        hero.getInventory().displayContents(0);
        System.out.println("\nTotal value: " + hero.getInventory().getValue() + "g");
        System.out.println("═══════════════════════════════════════");
        System.out.println("\nPress ENTER to continue...");
        scanner.nextLine();
    }
}


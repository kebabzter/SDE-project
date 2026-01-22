import java.util.Scanner;

import decorator.FlameEnchantment;
import decorator.FrostEnchantment;
import decorator.LifestealEnchantment;
import decorator.PoisonEnchantment;
import decorator.WeaponComponent;
import decorator.WeaponDecorator;
import model.Hero;

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
    
    /**
     * Enchants the hero's weapon using the Decorator pattern.
     * Demonstrates stacking multiple decorators on a single weapon.
     * 
     * DECORATOR PATTERN USAGE:
     * Each enchantment is a concrete decorator that wraps the current weapon.
     * Multiple enchantments can be stacked: Flame(Frost(BasicWeapon))
     * Each decorator adds its bonus while delegating to the wrapped component.
     */
    private void enchantWeapon(Hero hero) {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║         ENCHANTMENT MENU              ║");
        System.out.println("╠═══════════════════════════════════════╣");
        System.out.println("║  [1] Flame Enchant   - 80g (+7 fire)  ║");
        System.out.println("║  [2] Frost Enchant   - 60g (+5 cold)  ║");
        System.out.println("║  [3] Vampiric Enchant- 100g (+3, 15%ls)║");
        System.out.println("║  [4] Toxic Enchant   - 70g (+4 poison)║");
        System.out.println("║  [0] Back                             ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.println("\nCurrent weapon: " + hero.getWeapon().getDescription());
        System.out.print("\nYour choice: ");
        
        String choice = scanner.nextLine().trim();
        
        // Get the current weapon (could already be decorated)
        WeaponComponent currentWeapon = hero.getWeapon();
        
        switch (choice) {
            case "1" -> applyEnchantment(hero, currentWeapon, new FlameEnchantment(currentWeapon), 80, "Flame");
            case "2" -> applyEnchantment(hero, currentWeapon, new FrostEnchantment(currentWeapon), 60, "Frost");
            case "3" -> applyEnchantment(hero, currentWeapon, new LifestealEnchantment(currentWeapon), 100, "Vampiric");
            case "4" -> applyEnchantment(hero, currentWeapon, new PoisonEnchantment(currentWeapon), 70, "Toxic");
            case "0" -> { return; }
            default -> System.out.println("Invalid choice.");
        }
        
        System.out.println("\nPress ENTER to continue...");
        scanner.nextLine();
    }
    
    /**
     * Helper method to apply an enchantment (decorator) to a weapon.
     * Demonstrates the Decorator pattern: wrapping the existing weapon
     * with a new decorator to add functionality.
     */
    private void applyEnchantment(Hero hero, WeaponComponent currentWeapon, 
                                   WeaponDecorator enchantedWeapon, int cost, String enchantName) {
        if (hero.getGold() >= cost) {
            hero.removeGold(cost);
            
            // Decorator pattern: wrap current weapon with the new decorator
            // The enchantedWeapon already wraps currentWeapon (passed in constructor)
            hero.equipWeapon(enchantedWeapon);
            
            System.out.println("✓ Weapon enchanted with " + enchantName + "!");
            System.out.println("  New weapon: " + enchantedWeapon.getDescription());
        } else {
            System.out.println("✗ Not enough gold! (Need " + cost + "g)");
        }
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

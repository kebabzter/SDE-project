import java.util.Scanner;

public class Shop {
    private final Scanner scanner;
    
    public Shop(Scanner scanner) {
        this.scanner = scanner;
    }

    public void open(Hero hero) {
        boolean shopping = true;
        
        while (shopping) {
            displayShop(hero);
            
            System.out.print("\nYour choice: ");
            String input = scanner.nextLine().trim();
            
            switch (input) {
                case "1" -> buyHealthPotion(hero);
                case "2" -> buyAttackUpgrade(hero);
                case "3" -> buyDefenseUpgrade(hero);
                case "4" -> buyFullHeal(hero);
                case "0" -> shopping = false;
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
        
        System.out.println("\nLeaving shop...\n");
    }
    
    private void displayShop(Hero hero) {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║            THE PIT SHOP               ║");
        System.out.println("╠═══════════════════════════════════════╣");
        System.out.println("║  Your Gold: " + hero.getGold() + "g");
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
    }
}


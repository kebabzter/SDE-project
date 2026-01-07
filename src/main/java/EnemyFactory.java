/**
 * Factory Pattern Implementation for Creating Different Enemy Types
 * 
 * The EnemyFactory is a creational design pattern that encapsulates
 * the creation of different enemy types. Instead of directly instantiating
 * Enemy subclasses throughout the code, clients use the factory to create
 * enemies based on type and difficulty level.
 * 
 * Benefits:
 * - Centralizes enemy creation logic
 * - Easy to add new enemy types without changing client code
 * - Allows scaling enemy stats based on level/difficulty
 * - Reduces code duplication across the codebase
 */
public class EnemyFactory {
    
    /**
     * Enum for available enemy types to prevent invalid types
     */
    public enum EnemyType {
        GOBLIN("Goblin", 0.8),
        SKELETON("Skeleton", 1.0),
        ORC("Orc", 1.2),
        DARK_MAGE("Dark Mage", 1.1),
        DEMON("Demon", 1.4);
        
        private final String displayName;
        private final double difficultyMultiplier;
        
        EnemyType(String displayName, double multiplier) {
            this.displayName = displayName;
            this.difficultyMultiplier = multiplier;
        }
        
        public String getDisplayName() {
            return displayName;
        }
        
        public double getDifficultyMultiplier() {
            return difficultyMultiplier;
        }
    }
    
    /**
     * Creates an enemy of a specific type scaled to the given level
     * 
     * @param type The EnemyType to create
     * @param level The difficulty level (affects stats)
     * @return A new Enemy instance with scaled stats and AI strategy
     */
    public static Enemy createEnemy(EnemyType type, int level) {
        double multiplier = type.getDifficultyMultiplier();
        
        // Base stats scaled by level and enemy type multiplier
        int baseHealth = 30;
        int baseAttack = 5;
        int baseDefense = 2;
        int baseGold = 10;
        
        int scaledHealth = (int)(baseHealth + (level * 10 * multiplier));
        int scaledAttack = (int)(baseAttack + (level * 2 * multiplier));
        int scaledDefense = (int)(baseDefense + (level * multiplier));
        int scaledGold = (int)(baseGold + (level * 5 * multiplier));
        
        Enemy enemy = new Enemy(
            type.getDisplayName(),
            scaledHealth,
            scaledAttack,
            scaledDefense,
            scaledGold
        );
        
        // Assign different AI strategies based on enemy type
        assignStrategy(enemy, type);
        
        return enemy;
    }
    
    /**
     * Assigns an appropriate AI strategy to the enemy based on its type
     * 
     * @param enemy The enemy to assign a strategy to
     * @param type The enemy type
     */
    private static void assignStrategy(Enemy enemy, EnemyType type) {
        switch (type) {
            case GOBLIN:
                // Goblins are cowardly, prefer defense
                enemy.setStrategy(new DefensiveAI());
                break;
            case SKELETON:
                // Skeletons are relentless, always attack
                enemy.setStrategy(new AggressiveAI());
                break;
            case ORC:
                // Orcs are strong and adaptable
                enemy.setStrategy(new SmartAI());
                break;
            case DARK_MAGE:
                // Dark mages are strategic and intelligent
                enemy.setStrategy(new SmartAI());
                break;
            case DEMON:
                // Demons are ruthless and aggressive
                enemy.setStrategy(new AggressiveAI());
                break;
            default:
                enemy.setStrategy(new AggressiveAI());
        }
    }
    
    /**
     * Creates a random enemy scaled to the given level
     * 
     * @param level The difficulty level (affects stats)
     * @return A new random Enemy instance
     */
    public static Enemy createRandomEnemy(int level) {
        EnemyType[] types = EnemyType.values();
        EnemyType randomType = types[(level - 1) % types.length];
        return createEnemy(randomType, level);
    }
    
    /**
     * Creates an enemy by index (useful for cycling through types)
     * 
     * @param typeIndex Index into the EnemyType array (0-4)
     * @param level The difficulty level (affects stats)
     * @return A new Enemy instance of the specified type
     */
    public static Enemy createEnemyByIndex(int typeIndex, int level) {
        EnemyType[] types = EnemyType.values();
        EnemyType type = types[typeIndex % types.length];
        return createEnemy(type, level);
    }
    
    /**
     * Gets all available enemy types for documentation/menu purposes
     * 
     * @return Array of all EnemyType enum values
     */
    public static EnemyType[] getAllEnemyTypes() {
        return EnemyType.values();
    }
}

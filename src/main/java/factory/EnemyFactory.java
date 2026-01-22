package factory;

import java.util.HashMap;
import java.util.Map;
import model.Enemy;

/**
 * Factory Method Pattern - Creator Registry and Client Interface
 * 
 * This class serves as a registry for EnemyCreator instances and provides
 * a convenient interface for creating enemies. It demonstrates how the
 * Factory Method pattern can be used in practice: clients interact with
 * this class, which delegates to the appropriate concrete creator.
 * 
 * PATTERN EXPLANATION (Factory Method):
 * Unlike a Simple Factory that uses conditionals to decide what to create,
 * the Factory Method pattern uses polymorphism. Each EnemyCreator subclass
 * (GoblinCreator, SkeletonCreator, etc.) overrides the createEnemy() method
 * to produce its specific enemy type.
 * 
 * Structure (matching Refactoring Guru):
 * - EnemyCreator: Abstract Creator with factory method createEnemy()
 * - GoblinCreator, SkeletonCreator, etc.: Concrete Creators
 * - Enemy: Product
 * - This class: Client code that uses creators
 * 
 * Benefits over Simple Factory:
 * 1. Open/Closed Principle: Add new enemy types by creating new Creator classes
 * 2. Single Responsibility: Each creator handles one enemy type
 * 3. Polymorphism over conditionals: No switch statements for type selection
 * 
 * @see <a href="https://refactoring.guru/design-patterns/factory-method">Refactoring Guru - Factory Method</a>
 */
public class EnemyFactory {
    
    /**
     * Enum for available enemy types - used for indexing and iteration
     */
    public enum EnemyType {
        GOBLIN,
        SKELETON,
        ORC,
        DARK_MAGE,
        DEMON
    }
    
    // Registry mapping enemy types to their concrete creators
    // This is the key difference from Simple Factory: we use Creator objects
    // instead of conditional logic
    private static final Map<EnemyType, EnemyCreator> creators = new HashMap<>();
    
    // Static initialization of the creator registry
    static {
        // Each creator is responsible for creating its specific enemy type
        // Adding a new enemy type only requires:
        // 1. Creating a new EnemyCreator subclass
        // 2. Adding it to this registry
        // No modification of creation logic needed!
        creators.put(EnemyType.GOBLIN, new GoblinCreator());
        creators.put(EnemyType.SKELETON, new SkeletonCreator());
        creators.put(EnemyType.ORC, new OrcCreator());
        creators.put(EnemyType.DARK_MAGE, new DarkMageCreator());
        creators.put(EnemyType.DEMON, new DemonCreator());
    }
    
    /**
     * Creates an enemy using the Factory Method pattern.
     * This method delegates to the appropriate concrete creator based on type.
     * 
     * @param type The EnemyType to create
     * @param level The difficulty level (affects stats)
     * @return A new Enemy instance created by the appropriate creator
     */
    public static Enemy createEnemy(EnemyType type, int level) {
        // Get the creator for this enemy type
        EnemyCreator creator = creators.get(type);
        
        if (creator == null) {
            throw new IllegalArgumentException("Unknown enemy type: " + type);
        }
        
        // Delegate creation to the concrete creator (Factory Method pattern)
        // The creator's createEnemy() method is the actual factory method
        return creator.createEnemy(level);
    }
    
    /**
     * Gets the creator for a specific enemy type.
     * This allows clients to work directly with creators if needed.
     * 
     * @param type The enemy type
     * @return The EnemyCreator for that type
     */
    public static EnemyCreator getCreator(EnemyType type) {
        return creators.get(type);
    }
    
    /**
     * Creates a random enemy scaled to the given level.
     * Cycles through enemy types based on level.
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
     * Creates an enemy by index (useful for cycling through types).
     * 
     * @param typeIndex Index into the EnemyType array
     * @param level The difficulty level (affects stats)
     * @return A new Enemy instance of the specified type
     */
    public static Enemy createEnemyByIndex(int typeIndex, int level) {
        EnemyType[] types = EnemyType.values();
        EnemyType type = types[typeIndex % types.length];
        return createEnemy(type, level);
    }
    
    /**
     * Gets all available enemy types for documentation/menu purposes.
     * 
     * @return Array of all EnemyType enum values
     */
    public static EnemyType[] getAllEnemyTypes() {
        return EnemyType.values();
    }
    
    /**
     * Registers a new creator for an enemy type.
     * This demonstrates the extensibility of Factory Method pattern:
     * new enemy types can be added at runtime without modifying existing code.
     * 
     * @param type The enemy type
     * @param creator The creator for that type
     */
    public static void registerCreator(EnemyType type, EnemyCreator creator) {
        creators.put(type, creator);
    }
}

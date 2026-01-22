package factory;

import model.Enemy;
import strategy.EnemyAI;

/**
 * Factory Method Pattern - Abstract Creator
 * 
 * The Factory Method pattern defines an interface for creating objects,
 * but lets subclasses decide which classes to instantiate. This differs from
 * Simple Factory by using inheritance: each concrete creator subclass is
 * responsible for creating a specific product type.
 * 
 * According to Refactoring Guru:
 * - The Creator declares the factory method that returns new product objects
 * - Subclasses override the factory method to change the product type
 * - The factory method doesn't have to create new instances every time
 * 
 * Benefits:
 * - Eliminates tight coupling between creator and concrete products
 * - Single Responsibility: product creation code is in one place
 * - Open/Closed: new enemy types can be added without modifying existing code
 * 
 * @see <a href="https://refactoring.guru/design-patterns/factory-method">Refactoring Guru - Factory Method</a>
 */
public abstract class EnemyCreator {
    
    // Base stats that concrete creators can modify
    protected static final int BASE_HEALTH = 30;
    protected static final int BASE_ATTACK = 5;
    protected static final int BASE_DEFENSE = 2;
    protected static final int BASE_GOLD = 10;
    
    /**
     * The Factory Method - subclasses must override this to create specific enemy types.
     * This is the core of the Factory Method pattern: the creation logic is deferred
     * to subclasses, allowing each creator to produce a different type of enemy.
     * 
     * @param level The difficulty level affecting enemy stats
     * @return A new Enemy instance of the type this creator produces
     */
    public abstract Enemy createEnemy(int level);
    
    /**
     * Returns the display name of the enemy type this creator produces.
     * Used for logging and UI purposes.
     * 
     * @return The enemy type name
     */
    public abstract String getEnemyTypeName();
    
    /**
     * Returns the difficulty multiplier for this enemy type.
     * Higher multipliers mean stronger enemies.
     * 
     * @return The difficulty multiplier (1.0 = baseline)
     */
    public abstract double getDifficultyMultiplier();
    
    /**
     * Template method that provides a common algorithm for stat calculation.
     * Concrete creators can use this to get scaled stats based on level.
     * 
     * @param baseStat The base stat value
     * @param level The current game level
     * @param perLevelBonus The amount to add per level
     * @return The scaled stat value
     */
    protected int calculateScaledStat(int baseStat, int level, double perLevelBonus) {
        return (int) (baseStat + (level * perLevelBonus * getDifficultyMultiplier()));
    }
    
    /**
     * Factory method helper - creates and configures an enemy with common setup.
     * This demonstrates another benefit of Factory Method: common initialization
     * logic can be shared while still allowing customization.
     * 
     * @param level The difficulty level
     * @param strategy The AI strategy to assign
     * @return A configured Enemy instance
     */
    protected Enemy createConfiguredEnemy(int level, EnemyAI strategy) {
        int health = calculateScaledStat(BASE_HEALTH, level, 10);
        int attack = calculateScaledStat(BASE_ATTACK, level, 2);
        int defense = calculateScaledStat(BASE_DEFENSE, level, 1);
        int gold = calculateScaledStat(BASE_GOLD, level, 5);
        
        Enemy enemy = new Enemy(getEnemyTypeName(), health, attack, defense, gold);
        enemy.setStrategy(strategy);
        return enemy;
    }
}

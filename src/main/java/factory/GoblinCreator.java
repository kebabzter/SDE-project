package factory;

import model.Enemy;
import strategy.DefensiveAI;

/**
 * Factory Method Pattern - Concrete Creator for Goblin enemies
 * 
 * This concrete creator implements the factory method to produce Goblin enemies.
 * Goblins are weak but cautious creatures that use defensive tactics.
 * 
 * Each concrete creator encapsulates all the knowledge about how to create
 * a specific product type, including its stats and behavior configuration.
 */
public class GoblinCreator extends EnemyCreator {
    
    // Goblins are the weakest enemy type
    private static final double DIFFICULTY_MULTIPLIER = 0.8;
    
    /**
     * Factory Method implementation - creates a Goblin enemy.
     * Goblins have lower stats but use defensive AI to survive longer.
     * 
     * @param level The difficulty level affecting stats
     * @return A new Goblin Enemy instance with DefensiveAI
     */
    @Override
    public Enemy createEnemy(int level) {
        // Goblins use defensive tactics - they're cowardly creatures
        return createConfiguredEnemy(level, new DefensiveAI());
    }
    
    @Override
    public String getEnemyTypeName() {
        return "Goblin";
    }
    
    @Override
    public double getDifficultyMultiplier() {
        return DIFFICULTY_MULTIPLIER;
    }
}

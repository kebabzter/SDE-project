package factory;

import model.Enemy;
import strategy.AggressiveAI;

/**
 * Factory Method Pattern - Concrete Creator for Demon enemies
 * 
 * This concrete creator implements the factory method to produce Demon enemies.
 * Demons are powerful fiends that attack with overwhelming aggression.
 */
public class DemonCreator extends EnemyCreator {
    
    // Demons are the strongest enemy type
    private static final double DIFFICULTY_MULTIPLIER = 1.4;
    
    /**
     * Factory Method implementation - creates a Demon enemy.
     * Demons use aggressive AI - they're brutal and relentless.
     * 
     * @param level The difficulty level affecting stats
     * @return A new Demon Enemy instance with AggressiveAI
     */
    @Override
    public Enemy createEnemy(int level) {
        // Demons are savage attackers driven by rage
        return createConfiguredEnemy(level, new AggressiveAI());
    }
    
    @Override
    public String getEnemyTypeName() {
        return "Demon";
    }
    
    @Override
    public double getDifficultyMultiplier() {
        return DIFFICULTY_MULTIPLIER;
    }
}

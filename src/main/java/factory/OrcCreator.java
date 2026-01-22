package factory;

import model.Enemy;
import strategy.SmartAI;

/**
 * Factory Method Pattern - Concrete Creator for Orc enemies
 * 
 * This concrete creator implements the factory method to produce Orc enemies.
 * Orcs are strong and intelligent warriors that use tactical combat strategies.
 */
public class OrcCreator extends EnemyCreator {
    
    // Orcs are tougher than average
    private static final double DIFFICULTY_MULTIPLIER = 1.2;
    
    /**
     * Factory Method implementation - creates an Orc enemy.
     * Orcs use smart AI - they're cunning warriors who adapt to the battle.
     * 
     * @param level The difficulty level affecting stats
     * @return A new Orc Enemy instance with SmartAI
     */
    @Override
    public Enemy createEnemy(int level) {
        // Orcs are intelligent fighters who assess threats
        return createConfiguredEnemy(level, new SmartAI());
    }
    
    @Override
    public String getEnemyTypeName() {
        return "Orc";
    }
    
    @Override
    public double getDifficultyMultiplier() {
        return DIFFICULTY_MULTIPLIER;
    }
}

package factory;

import model.Enemy;
import strategy.AggressiveAI;

/**
 * Concrete Creator for Demon enemies
 */
public class DemonCreator extends EnemyCreator {
    
    private static final double DIFFICULTY_MULTIPLIER = 1.4;
    
    @Override
    public Enemy createEnemy(int level) {
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

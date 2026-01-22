package factory;

import model.Enemy;
import strategy.SmartAI;

/**
 * Concrete Creator for Orc enemies
 */
public class OrcCreator extends EnemyCreator {
    
    private static final double DIFFICULTY_MULTIPLIER = 1.2;
    
    @Override
    public Enemy createEnemy(int level) {
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

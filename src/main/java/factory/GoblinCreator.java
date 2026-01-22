package factory;

import model.Enemy;
import strategy.DefensiveAI;

/**
 * Concrete Creator for Goblin enemies
 */
public class GoblinCreator extends EnemyCreator {
    
    private static final double DIFFICULTY_MULTIPLIER = 0.8;
    
    @Override
    public Enemy createEnemy(int level) {
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

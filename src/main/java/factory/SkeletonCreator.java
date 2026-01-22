package factory;

import model.Enemy;
import strategy.AggressiveAI;

/**
 * Concrete Creator for Skeleton enemies
 */
public class SkeletonCreator extends EnemyCreator {
    
    private static final double DIFFICULTY_MULTIPLIER = 1.0;
    
    @Override
    public Enemy createEnemy(int level) {
        return createConfiguredEnemy(level, new AggressiveAI());
    }
    
    @Override
    public String getEnemyTypeName() {
        return "Skeleton";
    }
    
    @Override
    public double getDifficultyMultiplier() {
        return DIFFICULTY_MULTIPLIER;
    }
}

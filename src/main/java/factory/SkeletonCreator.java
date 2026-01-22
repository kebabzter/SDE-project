package factory;

import model.Enemy;
import strategy.AggressiveAI;

/**
 * Factory Method Pattern - Concrete Creator for Skeleton enemies
 * 
 * This concrete creator implements the factory method to produce Skeleton enemies.
 * Skeletons are undead warriors that attack relentlessly without self-preservation.
 */
public class SkeletonCreator extends EnemyCreator {
    
    // Skeletons are baseline difficulty
    private static final double DIFFICULTY_MULTIPLIER = 1.0;
    
    /**
     * Factory Method implementation - creates a Skeleton enemy.
     * Skeletons use aggressive AI - they know no fear and attack constantly.
     * 
     * @param level The difficulty level affecting stats
     * @return A new Skeleton Enemy instance with AggressiveAI
     */
    @Override
    public Enemy createEnemy(int level) {
        // Skeletons are mindless attackers - they never retreat
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

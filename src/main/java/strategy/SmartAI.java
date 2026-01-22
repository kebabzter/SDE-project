package strategy;

import model.Enemy;

/**
 * Concrete Strategy - adapts based on battle state
 */
public class SmartAI implements EnemyAI {
    private static final double LOW_HEALTH_THRESHOLD = 0.4;
    private static final double WEAK_HERO_THRESHOLD = 0.3;
    
    @Override
    public Action selectAction(Enemy enemy, int heroHealth, int heroMaxHealth) {
        double enemyHealthPercent = (double) enemy.getHealth() / enemy.getMaxHealth();
        double heroHealthPercent = (double) heroHealth / heroMaxHealth;
        
        if (enemyHealthPercent < LOW_HEALTH_THRESHOLD) {
            return Action.DEFEND;
        }
        
        if (heroHealthPercent <= WEAK_HERO_THRESHOLD) {
            return Action.ATTACK;
        }
        
        if (heroHealthPercent > enemyHealthPercent) {
            return Action.ATTACK;
        } else {
            return Action.DEFEND;
        }
    }
    
    @Override
    public String getStrategyName() {
        return "Smart";
    }
    
    @Override
    public String getDescription() {
        return "Adapts tactics based on battle state - dangerous and cunning";
    }
}

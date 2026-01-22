package strategy;

import model.Enemy;

/**
 * Concrete Strategy - defends when health is low
 */
public class DefensiveAI implements EnemyAI {
    private static final double DEFEND_THRESHOLD = 0.5;
    
    @Override
    public Action selectAction(Enemy enemy, int heroHealth, int heroMaxHealth) {
        double healthPercentage = (double) enemy.getHealth() / enemy.getMaxHealth();
        
        if (healthPercentage <= DEFEND_THRESHOLD) {
            return Action.DEFEND;
        }
        
        return Action.ATTACK;
    }
    
    @Override
    public String getStrategyName() {
        return "Defensive";
    }
    
    @Override
    public String getDescription() {
        return "Defends when wounded - tactically cautious";
    }
}

package strategy;

import model.Enemy;

/**
 * Concrete Strategy - always attacks
 */
public class AggressiveAI implements EnemyAI {
    
    @Override
    public Action selectAction(Enemy enemy, int heroHealth, int heroMaxHealth) {
        return Action.ATTACK;
    }
    
    @Override
    public String getStrategyName() {
        return "Aggressive";
    }
    
    @Override
    public String getDescription() {
        return "Always attacks - relentless and dangerous";
    }
}

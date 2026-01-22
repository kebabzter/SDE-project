package strategy;

import model.Enemy;

/**
 * DefensiveAI - Concrete Strategy Implementation
 * 
 * Prioritizes defense when health is low. Switches between attack and defend
 * based on remaining health percentage.
 * Best for tanky enemies that want to survive longer.
 */
public class DefensiveAI implements EnemyAI {
    private static final double DEFEND_THRESHOLD = 0.5; // Defend at 50% health or below
    
    @Override
    public Action selectAction(Enemy enemy, int heroHealth, int heroMaxHealth) {
        // If at or below 50% health, defend to reduce incoming damage
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

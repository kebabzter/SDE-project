/**
 * SmartAI - Concrete Strategy Implementation
 * 
 * Uses intelligent decision making based on the battle state.
 * - Attacks when hero is weak
 * - Defends when self is weak
 * - Adapts based on threat assessment
 * Best for tough bosses that require strategy to defeat.
 */
public class SmartAI implements EnemyAI {
    private static final double LOW_HEALTH_THRESHOLD = 0.4;  // Defend at 40% health
    private static final double WEAK_HERO_THRESHOLD = 0.3;   // Attack if hero is at 30% or less
    
    @Override
    public Action selectAction(Enemy enemy, int heroHealth, int heroMaxHealth) {
        double enemyHealthPercent = (double) enemy.getHealth() / enemy.getMaxHealth();
        double heroHealthPercent = (double) heroHealth / heroMaxHealth;
        
        // If self is critically wounded, defend
        if (enemyHealthPercent < LOW_HEALTH_THRESHOLD) {
            return Action.DEFEND;
        }
        
        // If hero is nearly defeated, finish them with attack
        if (heroHealthPercent <= WEAK_HERO_THRESHOLD) {
            return Action.ATTACK;
        }
        
        // Otherwise, balance between attack and defense
        // Attack if hero is at moderate health, defend if both are similar
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

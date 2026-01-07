/**
 * AggressiveAI - Concrete Strategy Implementation
 * 
 * Always attacks the hero. High damage output but no defensive tactics.
 * Best for strong enemies that want to eliminate threats quickly.
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

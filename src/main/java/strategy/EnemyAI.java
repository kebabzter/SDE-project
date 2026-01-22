package strategy;

import model.Enemy;

/**
 * Strategy Pattern - defines different AI behaviors for enemies
 */
public interface EnemyAI {
    
    enum Action {
        ATTACK("Attack"),
        DEFEND("Defend"),
        HEAL("Heal");
        
        private final String displayName;
        
        Action(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    Action selectAction(Enemy enemy, int heroHealth, int heroMaxHealth);
    String getStrategyName();
    String getDescription();
}

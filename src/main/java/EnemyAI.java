/**
 * EnemyAI Interface - Strategy Pattern Implementation
 * 
 * The Strategy pattern defines a family of algorithms, encapsulates each one,
 * and makes them interchangeable. Different enemy types can have different AI
 * strategies that determine their combat behavior.
 * 
 * Benefits:
 * - Easy to switch AI strategies at runtime
 * - Each strategy is independent and testable
 * - Adding new AI behaviors doesn't require modifying Enemy class
 * - Strategies can be shared between different enemy types
 */
public interface EnemyAI {
    
    /**
     * Enum for possible enemy actions in combat
     */
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
    
    /**
     * Determines what action the enemy should take based on current battle state
     * 
     * @param enemy The enemy making the decision
     * @param heroHealth The hero's current health
     * @param heroMaxHealth The hero's maximum health
     * @return The action the enemy should take
     */
    Action selectAction(Enemy enemy, int heroHealth, int heroMaxHealth);
    
    /**
     * Returns the name of this AI strategy
     * @return Strategy name for logging/debugging
     */
    String getStrategyName();
    
    /**
     * Returns a description of this AI's behavior
     * @return Behavior description
     */
    String getDescription();
}

/**
 * HeroState Interface - Behavioral Design Pattern
 * 
 * The State pattern allows an object (Hero) to alter its behavior
 * when its internal state changes. The Hero delegates its behavior
 * to different state objects rather than implementing all behavior itself.
 * 
 * Benefits:
 * - Hero behavior changes based on current state
 * - Easy to add new states without modifying Hero class
 * - State-specific logic is encapsulated in state classes
 * - Supports state transitions with clear entry/exit points
 */
public interface HeroState {
    
    /**
     * Called when the hero enters this state
     * @param hero The hero entering this state
     */
    void onEnter(Hero hero);
    
    /**
     * Called when the hero exits this state
     * @param hero The hero leaving this state
     */
    void onExit(Hero hero);
    
    /**
     * Modifies the hero's attack power based on state
     * @param baseAttack The base attack value
     * @return Modified attack value for this state
     */
    int modifyAttack(int baseAttack);
    
    /**
     * Modifies the hero's defense based on state
     * @param baseDefense The base defense value
     * @return Modified defense value for this state
     */
    int modifyDefense(int baseDefense);
    
    /**
     * Determines if the hero can perform an action in this state
     * @return true if the hero can act, false otherwise
     */
    boolean canAct();
    
    /**
     * Returns the display name of this state
     * @return State name for UI display
     */
    String getStateName();
    
    /**
     * Returns a description of the state effects
     * @return State effect description
     */
    String getStateDescription();
    
    /**
     * Decrement the state duration (for temporary states)
     * @return true if the state should end, false if it continues
     */
    boolean decrementDuration();
}

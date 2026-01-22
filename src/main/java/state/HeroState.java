package state;

import model.Hero;

/**
 * State Pattern - State Interface
 * 
 * The State pattern allows an object (Hero/Context) to alter its behavior
 * when its internal state changes. The Hero delegates behavior to different 
 * state objects rather than implementing all behavior itself.
 * 
 * PATTERN EXPLANATION (State - Refactoring Guru):
 * 
 * Structure:
 * - Context (Hero): Maintains a reference to a State instance and delegates
 *   state-specific behavior to it
 * - State (this interface): Declares methods for state-specific behavior
 * - Concrete States: Implement behavior for specific states AND contain
 *   reference back to Context to trigger state transitions
 * 
 * KEY REQUIREMENT (per Refactoring Guru):
 * Each concrete state must have access to the Context. States are responsible
 * for deciding and triggering transitions to other states. The Context does
 * NOT decide transitions; it only provides a method for states to change.
 * 
 * Benefits:
 * - Single Responsibility: State-specific behavior is in state classes
 * - Open/Closed: New states can be added without modifying existing code
 * - States control their own transitions (no complex conditionals in Context)
 * 
 * @see <a href="https://refactoring.guru/design-patterns/state">Refactoring Guru - State</a>
 */
public interface HeroState {
    
    /**
     * Sets the context (Hero) reference for this state.
     * CRITICAL FOR PATTERN CORRECTNESS: States must have access to context
     * so they can trigger state transitions themselves.
     * 
     * This is called automatically when Hero.setState() is invoked.
     * 
     * @param context The Hero that owns this state
     */
    void setContext(Hero context);
    
    /**
     * Called when the hero enters this state.
     * States can perform initialization here.
     */
    void onEnter();
    
    /**
     * Called when the hero exits this state.
     * States can perform cleanup here.
     */
    void onExit();
    
    /**
     * Modifies the hero's attack power based on state.
     * @param baseAttack The base attack value
     * @return Modified attack value for this state
     */
    int modifyAttack(int baseAttack);
    
    /**
     * Modifies the hero's defense based on state.
     * @param baseDefense The base defense value
     * @return Modified defense value for this state
     */
    int modifyDefense(int baseDefense);
    
    /**
     * Determines if the hero can perform an action in this state.
     * @return true if the hero can act, false otherwise
     */
    boolean canAct();
    
    /**
     * Returns the display name of this state.
     * @return State name for UI display
     */
    String getStateName();
    
    /**
     * Returns a description of the state effects.
     * @return State effect description
     */
    String getStateDescription();
    
    /**
     * Handles state update logic each turn.
     * STATE TRANSITIONS HAPPEN HERE: The state is responsible for
     * checking conditions and triggering transitions via the context.
     * 
     * This replaces the old decrementDuration() method - states now
     * actively manage their own lifecycle and transitions.
     */
    void handleTurnUpdate();
    
    /**
     * Gets any per-turn damage this state applies (e.g., poison damage).
     * @return The damage amount, or 0 if no damage
     */
    default int getTurnDamage() {
        return 0;
    }
}

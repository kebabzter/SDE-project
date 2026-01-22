package state;

import model.Hero;

/**
 * State Pattern - Concrete State (Normal)
 * 
 * The normal state where the hero has no afflictions or buffs.
 * Attack and defense are not modified, and the hero can act freely.
 * 
 * This state doesn't transition to other states on its own.
 * External events (enemy attacks) cause transitions to other states.
 */
public class NormalState implements HeroState {
    
    // Reference to the context (Hero) - required for State pattern
    // States use this to trigger transitions to other states
    private Hero context;
    
    /**
     * Sets the context reference.
     * This allows the state to trigger transitions via context.setState()
     */
    @Override
    public void setContext(Hero context) {
        this.context = context;
    }
    
    @Override
    public void onEnter() {
        if (context != null) {
            System.out.println("✓ " + context.getName() + " returns to normal!");
        }
    }
    
    @Override
    public void onExit() {
        // No special exit behavior for normal state
    }
    
    @Override
    public int modifyAttack(int baseAttack) {
        return baseAttack; // No modification
    }
    
    @Override
    public int modifyDefense(int baseDefense) {
        return baseDefense; // No modification
    }
    
    @Override
    public boolean canAct() {
        return true; // Hero can always act in normal state
    }
    
    @Override
    public String getStateName() {
        return "Normal";
    }
    
    @Override
    public String getStateDescription() {
        return "No afflictions or buffs";
    }
    
    /**
     * Normal state doesn't transition on its own.
     * Transitions to other states are triggered by external events.
     */
    @Override
    public void handleTurnUpdate() {
        // Normal state is stable - no automatic transitions
        // Other states (Poisoned, Stunned) transition back here when they expire
    }
}

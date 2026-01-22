package state;

import model.Hero;

/**
 * State Pattern - Concrete State (Poisoned)
 * 
 * When poisoned, the hero's attack power is reduced by 40% as the toxins
 * weaken their strikes. The poison persists for 3 turns before wearing off.
 * Additionally, the hero takes damage each turn from the poison.
 * 
 * KEY STATE PATTERN FEATURE:
 * This state is responsible for managing its own duration and triggering
 * the transition back to NormalState when the poison wears off. The Context
 * (Hero) doesn't decide when to transition - the State does.
 */
public class PoisonedState implements HeroState {
    
    // Reference to the context - required for triggering state transitions
    private Hero context;
    
    private int turnsRemaining;
    private static final int POISON_DURATION = 3;
    private static final int POISON_DAMAGE_PER_TURN = 5;
    private static final double ATTACK_REDUCTION = 0.6; // 40% reduction (60% of normal)
    
    public PoisonedState() {
        this.turnsRemaining = POISON_DURATION;
    }
    
    @Override
    public void setContext(Hero context) {
        this.context = context;
    }
    
    @Override
    public void onEnter() {
        if (context != null) {
            System.out.println("☠ " + context.getName() + " has been POISONED! (lasts " + POISON_DURATION + " turns)");
        }
    }
    
    @Override
    public void onExit() {
        System.out.println("✓ The poison wears off!");
    }
    
    @Override
    public int modifyAttack(int baseAttack) {
        // Poison reduces attack by 40%
        return (int)(baseAttack * ATTACK_REDUCTION);
    }
    
    @Override
    public int modifyDefense(int baseDefense) {
        return baseDefense; // Poison doesn't affect defense
    }
    
    @Override
    public boolean canAct() {
        return true; // Poisoned hero can still act (just weakened)
    }
    
    @Override
    public String getStateName() {
        return "Poisoned";
    }
    
    @Override
    public String getStateDescription() {
        return "Attack reduced by 40% | Takes " + POISON_DAMAGE_PER_TURN + " damage per turn | " + turnsRemaining + " turns remaining";
    }
    
    /**
     * STATE TRANSITION LOGIC:
     * The state manages its own duration and triggers transition to NormalState
     * when poison expires. This follows Refactoring Guru's State pattern:
     * "States are responsible for transitioning to other states."
     */
    @Override
    public void handleTurnUpdate() {
        turnsRemaining--;
        
        // State decides when to transition - not the Context (Hero)
        if (turnsRemaining <= 0 && context != null) {
            // Trigger transition to NormalState
            // The state is responsible for this decision, not the Hero
            context.setState(new NormalState());
        }
    }
    
    @Override
    public int getTurnDamage() {
        return POISON_DAMAGE_PER_TURN;
    }
    
    /**
     * @deprecated Use getTurnDamage() instead
     */
    @Deprecated
    public int getPoisonDamage() {
        return POISON_DAMAGE_PER_TURN;
    }
}

package state;

import model.Hero;

/**
 * State Pattern - Concrete State (Stunned)
 * 
 * When stunned, the hero cannot act for 2 turns. This represents
 * complete disorientation and loss of control. The hero is vulnerable
 * but takes no damage from being stunned itself.
 * 
 * KEY STATE PATTERN FEATURE:
 * This state manages its own duration and triggers the transition
 * back to NormalState when the stun wears off. The state controls
 * when to transition, not the Context.
 */
public class StunnedState implements HeroState {
    
    // Reference to the context - required for triggering state transitions
    private Hero context;
    
    private int turnsRemaining;
    private static final int STUN_DURATION = 2;
    
    public StunnedState() {
        this.turnsRemaining = STUN_DURATION;
    }
    
    @Override
    public void setContext(Hero context) {
        this.context = context;
    }
    
    @Override
    public void onEnter() {
        if (context != null) {
            System.out.println("⭐ " + context.getName() + " has been STUNNED! (lasts " + STUN_DURATION + " turns)");
        }
    }
    
    @Override
    public void onExit() {
        if (context != null) {
            System.out.println("✓ " + context.getName() + " shakes off the stun!");
        }
    }
    
    @Override
    public int modifyAttack(int baseAttack) {
        return 0; // Cannot attack while stunned
    }
    
    @Override
    public int modifyDefense(int baseDefense) {
        return baseDefense / 2; // Defense is halved (easier to hit)
    }
    
    @Override
    public boolean canAct() {
        return false; // Stunned hero cannot perform any action
    }
    
    @Override
    public String getStateName() {
        return "Stunned";
    }
    
    @Override
    public String getStateDescription() {
        return "Cannot act | Defense halved | " + turnsRemaining + " turns remaining";
    }
    
    /**
     * STATE TRANSITION LOGIC:
     * The state manages its own duration and triggers transition to NormalState
     * when stun expires. This is the key aspect of the State pattern from
     * Refactoring Guru: states control their own transitions.
     */
    @Override
    public void handleTurnUpdate() {
        turnsRemaining--;
        
        // State decides when to transition - not the Context (Hero)
        if (turnsRemaining <= 0 && context != null) {
            // Trigger transition to NormalState
            context.setState(new NormalState());
        }
    }
}

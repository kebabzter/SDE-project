package state;

import model.Hero;

/**
 * Concrete State - Stunned
 * 
 * Prevents actions and reduces defense.
 * Triggers transition to NormalState when duration expires.
 */
public class StunnedState implements HeroState {
    
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
        return 0;
    }
    
    @Override
    public int modifyDefense(int baseDefense) {
        return baseDefense / 2;
    }
    
    @Override
    public boolean canAct() {
        return false;
    }
    
    @Override
    public String getStateName() {
        return "Stunned";
    }
    
    @Override
    public String getStateDescription() {
        return "Cannot act | Defense halved | " + turnsRemaining + " turns remaining";
    }
    
    @Override
    public void handleTurnUpdate() {
        turnsRemaining--;
        
        // State triggers its own transition when duration expires
        if (turnsRemaining <= 0 && context != null) {
            context.setState(new NormalState());
        }
    }
}

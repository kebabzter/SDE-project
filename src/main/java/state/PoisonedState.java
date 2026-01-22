package state;

import model.Hero;

/**
 * Concrete State - Poisoned
 * 
 * Reduces attack and deals damage per turn.
 * Triggers transition to NormalState when duration expires.
 */
public class PoisonedState implements HeroState {
    
    private Hero context;
    private int turnsRemaining;
    
    private static final int POISON_DURATION = 3;
    private static final int POISON_DAMAGE_PER_TURN = 5;
    private static final double ATTACK_REDUCTION = 0.6;
    
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
        return (int)(baseAttack * ATTACK_REDUCTION);
    }
    
    @Override
    public int modifyDefense(int baseDefense) {
        return baseDefense;
    }
    
    @Override
    public boolean canAct() {
        return true;
    }
    
    @Override
    public String getStateName() {
        return "Poisoned";
    }
    
    @Override
    public String getStateDescription() {
        return "Attack reduced by 40% | Takes " + POISON_DAMAGE_PER_TURN + " damage per turn | " + turnsRemaining + " turns remaining";
    }
    
    @Override
    public void handleTurnUpdate() {
        turnsRemaining--;
        
        // State triggers its own transition when duration expires
        if (turnsRemaining <= 0 && context != null) {
            context.setState(new NormalState());
        }
    }
    
    @Override
    public int getTurnDamage() {
        return POISON_DAMAGE_PER_TURN;
    }
}

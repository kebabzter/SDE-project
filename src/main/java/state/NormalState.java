package state;

import model.Hero;

/**
 * Concrete State - Normal (no status effects)
 */
public class NormalState implements HeroState {
    
    private Hero context;
    
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
    }
    
    @Override
    public int modifyAttack(int baseAttack) {
        return baseAttack;
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
        return "Normal";
    }
    
    @Override
    public String getStateDescription() {
        return "No afflictions or buffs";
    }
    
    @Override
    public void handleTurnUpdate() {
        // Normal state doesn't transition
    }
}

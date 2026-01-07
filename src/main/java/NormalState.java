/**
 * NormalState - Concrete State Implementation
 * 
 * The normal state where the hero has no afflictions or buffs.
 * Attack and defense are not modified, and the hero can act freely.
 */
public class NormalState implements HeroState {
    
    @Override
    public void onEnter(Hero hero) {
        System.out.println("✓ " + hero.getName() + " returns to normal!");
    }
    
    @Override
    public void onExit(Hero hero) {
        // No special exit behavior
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
    
    @Override
    public boolean decrementDuration() {
        return false; // Normal state doesn't expire
    }
}

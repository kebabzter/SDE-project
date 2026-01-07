/**
 * PoisonedState - Concrete State Implementation
 * 
 * When poisoned, the hero's attack power is reduced by 40% as the toxins
 * weaken their strikes. The poison persists for 3 turns before wearing off.
 * Additionally, the hero takes damage each turn from the poison.
 */
public class PoisonedState implements HeroState {
    private int turnsRemaining;
    private static final int POISON_DURATION = 3;
    private static final int POISON_DAMAGE_PER_TURN = 5;
    private static final double ATTACK_REDUCTION = 0.6; // 40% reduction (60% of normal)
    
    public PoisonedState() {
        this.turnsRemaining = POISON_DURATION;
    }
    
    @Override
    public void onEnter(Hero hero) {
        System.out.println("☠ " + hero.getName() + " has been POISONED! (lasts " + POISON_DURATION + " turns)");
    }
    
    @Override
    public void onExit(Hero hero) {
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
    
    @Override
    public boolean decrementDuration() {
        turnsRemaining--;
        return turnsRemaining <= 0;
    }
    
    public int getPoisonDamage() {
        return POISON_DAMAGE_PER_TURN;
    }
}

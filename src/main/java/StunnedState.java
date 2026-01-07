/**
 * StunnedState - Concrete State Implementation
 * 
 * When stunned, the hero cannot act for 2 turns. This represents
 * complete disorientation and loss of control. The hero is vulnerable
 * but takes no damage from being stunned itself.
 */
public class StunnedState implements HeroState {
    private int turnsRemaining;
    private static final int STUN_DURATION = 2;
    
    public StunnedState() {
        this.turnsRemaining = STUN_DURATION;
    }
    
    @Override
    public void onEnter(Hero hero) {
        System.out.println("⭐ " + hero.getName() + " has been STUNNED! (lasts " + STUN_DURATION + " turns)");
    }
    
    @Override
    public void onExit(Hero hero) {
        System.out.println("✓ " + hero.getName() + " shakes off the stun!");
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
    
    @Override
    public boolean decrementDuration() {
        turnsRemaining--;
        return turnsRemaining <= 0;
    }
}

package state;

import model.Hero;

/**
 * Concrete State – Poisoned.
 * Holds link to context; initiates transition to NormalState when duration expires.
 *
 * @see <a href="https://refactoring.guru/design-patterns/state">State – Refactoring.Guru</a>
 */
public class PoisonedState implements HeroState {

    private Hero context;
    private int turnsRemaining;

    private static final int POISON_DURATION = 3;
    private static final int POISON_DAMAGE_PER_TURN = 5;
    private static final double ATTACK_REDUCTION = 0.6;

    public PoisonedState() {}

    public PoisonedState(Hero context) {
        this.context = context;
    }

    @Override
    public void setContext(Hero context) {
        this.context = context;
    }

    @Override
    public Hero getContext() {
        return context;
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
        return (int) (baseAttack * ATTACK_REDUCTION);
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
        if (turnsRemaining <= 0 && context != null) {
            context.setState(new NormalState(context));
        }
    }

    @Override
    public int getTurnDamage() {
        return POISON_DAMAGE_PER_TURN;
    }
}

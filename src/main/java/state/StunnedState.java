package state;

import model.Hero;

/**
 * Concrete State – Stunned.
 * Holds link to context; initiates transition to NormalState when duration expires.
 *
 * @see <a href="https://refactoring.guru/design-patterns/state">State – Refactoring.Guru</a>
 */
public class StunnedState implements HeroState {

    private Hero context;
    private int turnsRemaining;

    private static final int STUN_DURATION = 2;

    public StunnedState() {}

    public StunnedState(Hero context) {
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
        if (turnsRemaining <= 0 && context != null) {
            context.setState(new NormalState(context));
        }
    }
}

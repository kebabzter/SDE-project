package state;

import model.Hero;

/**
 * Concrete State – Normal (no status effects).
 * Holds link to context; initiates transitions to Poisoned/Stunned when effects received.
 *
 * @see <a href="https://refactoring.guru/design-patterns/state">State – Refactoring.Guru</a>
 */
public class NormalState implements HeroState {

    private Hero context;

    public NormalState() {}

    public NormalState(Hero context) {
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
            System.out.println("✓ " + context.getName() + " returns to normal!");
        }
    }

    @Override
    public void onExit() {}

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
    public void handleTurnUpdate() {}

    @Override
    public void onReceivePoison() {
        if (context != null) {
            context.setState(new PoisonedState(context));
        }
    }

    @Override
    public void onReceiveStun() {
        if (context != null) {
            context.setState(new StunnedState(context));
        }
    }
}

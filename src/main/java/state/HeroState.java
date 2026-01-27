package state;

import model.Hero;

/**
 * State Pattern – State interface.
 * Declares state-specific methods. Concrete states implement them.
 *
 * States store a backreference to the Context (Hero). Through this link they
 * fetch context info and initiate state transitions. The context is set via
 * setContext; getContext exposes the link.
 *
 * @see <a href="https://refactoring.guru/design-patterns/state">State – Refactoring.Guru</a>
 */
public interface HeroState {

    void setContext(Hero context);

    /** Backreference to the context. States use it to initiate transitions. */
    Hero getContext();

    void onEnter();
    void onExit();

    int modifyAttack(int baseAttack);
    int modifyDefense(int baseDefense);
    boolean canAct();

    String getStateName();
    String getStateDescription();

    void handleTurnUpdate();

    default int getTurnDamage() {
        return 0;
    }

    default void onReceivePoison() {}

    default void onReceiveStun() {}
}

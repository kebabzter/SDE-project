package model;

import state.PoisonedState;
import state.StunnedState;
import strategy.EnemyAI;

/**
 * Product (Factory Method pattern).
 * Interface common to all objects produced by the creator and its subclasses.
 * Concrete products (Goblin, Skeleton, etc.) implement this interface.
 *
 * @see <a href="https://refactoring.guru/design-patterns/factory-method">Factory Method – Refactoring.Guru</a>
 */
public interface Enemy {

    String getName();
    int getHealth();
    int getMaxHealth();
    int getAttack();
    int getDefense();
    int getGoldReward();
    EnemyAI getStrategy();

    void setHealth(int health);
    void setStrategy(EnemyAI strategy);

    default void takeDamage(int damage) {
        int actualDamage = Math.max(1, damage - getDefense());
        setHealth(Math.max(0, getHealth() - actualDamage));
    }

    default void heal(int amount) {
        setHealth(Math.min(getMaxHealth(), getHealth() + amount));
    }

    default boolean isAlive() {
        return getHealth() > 0;
    }

    default void displayStatus() {
        System.out.println(getName() + " - HP: " + getHealth() + "/" + getMaxHealth()
                + " | ATK: " + getAttack() + " | DEF: " + getDefense());
    }

    default EnemyAI.Action decideAction(int heroHealth, int heroMaxHealth) {
        return getStrategy().selectAction(this, heroHealth, heroMaxHealth);
    }

    default void applyRandomEffect(Hero hero) {
        int random = (int) (Math.random() * 100);
        if (random < 25) {
            hero.setState(new PoisonedState());
        } else if (random < 50) {
            hero.setState(new StunnedState());
        }
    }
}
